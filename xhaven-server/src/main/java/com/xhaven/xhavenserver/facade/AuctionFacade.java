package com.xhaven.xhavenserver.facade;

import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.Image;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.service.AuctionService;
import com.xhaven.xhavenserver.service.ImageService;
import com.xhaven.xhavenserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionFacade {

    private final AuctionService auctionService;
    private final ImageService imageService;
    private final UserService userService;

    public Auction getAuctionById(Long auctionId) {
        Auction auction = auctionService.getAuctionById(auctionId);
        return auctionService.updateAuctionWithIsFavorite(imageService.getAuctionWithImageFiles(auction),
                userService.getCurrentUser());
    }

    public List<Auction> getAuctions(Long ownerId, Boolean isActive) {
        List<Auction> allAuctions = auctionService.getAuctions(ownerId, isActive).stream()
                .map(imageService::getAuctionWithImageFiles)
                .toList();
        return auctionService.getUpdatedAuctionsWithIsFavorite(allAuctions, userService.getCurrentUser());
    }

    //TODO think about transactional here or in services
    public void saveNewAuction(Auction auction, MultipartFile[] images) {
        List<Image> imageEntities = imageService.saveImagesToFilesystem(images);
        User owner = userService.getCurrentUser();
        auctionService.saveNewAuction(owner, auction, imageEntities);
    }

    //TODO think about transactional here?
    public void changeAuctionStatus(Long auctionId, boolean newIsActive, boolean isSold) {
        Auction auction = auctionService.getAuctionById(auctionId);
        auction.setIsActive(newIsActive);
        auctionService.updateAuction(auction);

        //TODO send notifications with kafka (isSold) to followers

        //TODO remove auction from followers
    }
}
