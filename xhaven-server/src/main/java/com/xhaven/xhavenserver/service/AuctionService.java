package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.Image;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.repository.AuctionRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final NotificationService notificationService;

    public Auction getAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found"));
    }

    public Auction getCompleteAuction(Long auctionId) {
        Auction auction = getAuctionWithImagesById(auctionId);
        auction = updateAuctionWithIsFavorite(auction, userService.getCurrentUser());
        return auction;
    }

    private Auction getAuctionWithImagesById(Long auctionId) {
        return imageService.getAuctionWithImageFiles(getAuctionById(auctionId));
    }

    public List<Auction> getAuctionsWithImagesAndFavoriteFlag(Long currentUserId, Long ownerId, Boolean isActive) {
        List<Auction> allAuctions = getAuctions(ownerId, isActive).stream()
                .map(imageService::getAuctionWithImageFiles)
                .toList();

        User currentUser = userService.getUserById(currentUserId);
        return getUpdatedAuctionsWithFavoriteFlag(allAuctions, currentUser);
    }

    public List<Auction> getAuctions(Long ownerId, Boolean isActive) {
        Specification<Auction> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(ownerId != null) {
                predicates.add(cb.equal(root.get("owner"), ownerId));
            }
            if(isActive != null) {
                predicates.add(cb.equal(root.get("isActive"), isActive));
            }

            Predicate andPredicate = null;
            for (Predicate predicate : predicates) {
                andPredicate = cb.and(predicate);
            }

            return andPredicate;
        };

        return auctionRepository.findAll(spec);
    }

    @Transactional
    public void saveNewAuction(Auction auction, MultipartFile[] images) {
        List<Image> imageEntities = imageService.saveImagesToFilesystem(images);
        auction.setPostedAt(LocalDateTime.now());
        auction.setIsActive(true);
        auction.setImages(imageEntities);
        auction.setOwner(userService.getCurrentUser());
        auctionRepository.save(auction);
    }

    public List<Auction> getUpdatedAuctionsWithFavoriteFlag(List<Auction> auctions, User currentUser) {
        return auctions.stream()
                .map(auction -> updateAuctionWithIsFavorite(auction, currentUser))
                .toList();
    }

    public Auction updateAuctionWithIsFavorite(Auction auction, User user) {
        auction.setIsFavorite(isAuctionFavorite(auction, user));
        return auction;
    }

    private boolean isAuctionFavorite(Auction auction, User user) {
        return user.getFavoriteAuctions().contains(auction);
    }

    @Transactional
    public void changeAuctionStatus(Long auctionId, boolean newIsActive, boolean wasSold) {
        Auction auction = getAuctionById(auctionId);
        auction.setIsActive(newIsActive);
        auctionRepository.save(auction);

        if(isAuctionBeingTakenDown(newIsActive)) {
            List<User> followers = userService.getFollowersOfAuction(auction);
            notificationService.sendAuctionTakenDownNotifications(auction, followers);
            followers.forEach(user -> user.removeFavoriteAuction(auction));
        }
    }

    private boolean isAuctionBeingTakenDown(boolean newIsActive) {
        return !newIsActive;
    }

    public List<Auction> getFavoriteAuctionsOfUser(Long currentUserId) {
        User currentUser = userService.getUserById(currentUserId);
        return currentUser.getFavoriteAuctions(); //TODO wrap in images and isFavorite
    }

    @Transactional
    public void addAuctionToFavorites(Long currentUserId, Long auctionId) {
        User currentUser = userService.getUserById(currentUserId);
        currentUser.addFavoriteAuction(getAuctionById(auctionId));
//        userService.saveUser(currentUser); //TODO is it needed?
    }

    public void removeAuctionFromFavorites(Long currentUserId, Long auctionId) {
        User currentUser = userService.getUserById(currentUserId);
        currentUser.removeFavoriteAuction(getAuctionById(auctionId));
//        userService.saveUser(currentUser); //TODO is it needed?
    }


}
