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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Auction getAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found"));
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
    public void saveNewAuction(User user, Auction auction, List<Image> images) { //TODO change to retrieving owner by id, not from security context?
        auction.setPostedAt(LocalDateTime.now());
        auction.setIsActive(true);
        auction.setImages(images);
        auction.setOwner(user);
        auctionRepository.save(auction);
    }

    public List<Auction> getUpdatedAuctionsWithIsFavorite(List<Auction> auctions, User currentUser) {
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

    public void updateAuction(Auction auction) {
        auctionRepository.save(auction);
    }
}
