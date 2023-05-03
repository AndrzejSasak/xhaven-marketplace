package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.Image;
import com.xhaven.xhavenserver.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ImageService imageService;
    private final UserService userService;

    public Auction getAuctionById(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found"));
        return imageService.getAuctionWithImageFiles(auction);
    }

    public List<Auction> getAuctionsByUserId(Long userId) {
        return auctionRepository.findAllByOwnerId(userId);
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Transactional
    public void saveNewAuction(MultipartFile[] images, Auction auction) {

        imageService.saveImagesToFilesystem(images);
        List<Image> imageEntities = imageService.createImageEntites(images);

        auction.setPostedAt(LocalDateTime.now());
        auction.setActive(true);
        auction.setImages(imageEntities);
        auction.setOwner(userService.getCurrentUser());

        auctionRepository.save(auction);
    }

    public List<Auction> getAuctions(Optional<Long> userId) {
        if(userId.isPresent()) {
            List<Auction> auctions = getAuctionsByUserId(userId.get());



            return auctions;
        } else {
            return getAllAuctions();
        }
    }


}
