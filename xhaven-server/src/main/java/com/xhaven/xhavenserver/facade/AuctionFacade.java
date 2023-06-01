package com.xhaven.xhavenserver.facade;

import com.xhaven.xhavenserver.dto.AuctionDto;
import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.Image;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.service.AuctionService;
import com.xhaven.xhavenserver.service.ImageService;
import com.xhaven.xhavenserver.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionFacade {

    private final KafkaTemplate<String, AuctionTakenDownNotificationDto> kafkaTemplate;
    private final ModelMapper modelMapper;
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

    public void saveNewAuction(Auction auction, MultipartFile[] images) {
        List<Image> imageEntities = imageService.saveImagesToFilesystem(images);
        User owner = userService.getCurrentUser();
        auctionService.saveNewAuction(owner, auction, imageEntities);
    }

    @Transactional
    public void changeAuctionStatus(Long auctionId, boolean newIsActive, boolean isSold) {
        Auction auction = auctionService.getAuctionById(auctionId);
        auction.setIsActive(newIsActive);
        auctionService.updateAuction(auction);

        if(!newIsActive) {
            List<User> followers = userService.getFollowersOfAuction(auction);
            sendAuctionTakenDownNotifications(auction, followers);
            followers.forEach(user -> user.removeFavoriteAuction(auction));
        }

    }

    private void sendAuctionTakenDownNotifications(Auction auction, List<User> followers) {
        kafkaTemplate.send("auction-taken-down-topic", buildNotification(auction, followers));
    }

    private AuctionTakenDownNotificationDto buildNotification(Auction auction, List<User> followers) {
        return AuctionTakenDownNotificationDto.builder()
                .auctionDto(modelMapper.map(auction, AuctionDto.class))
                .recipientsEmails(followers.stream()
                        .map(User::getEmail)
                        .toList())
                .build();
    }
}
