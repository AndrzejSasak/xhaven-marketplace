package com.xhaven.xhavenserver.facade;

import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.service.AuctionService;
import com.xhaven.xhavenserver.service.ImageService;
import com.xhaven.xhavenserver.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final AuctionService auctionService;
    private final ImageService imageService;

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    public User getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    public void addAuctionToFavorites(Long userId, Long auctionId) {
        User user = getUserById(userId);
        Auction auction = auctionService.getAuctionById(auctionId);
        userService.addAuctionToFavorites(user, auction);
    }

    public void removeAuctionFromFavorites(Long userId, Long auctionId) {
        User user = getUserById(userId);
        Auction auction = auctionService.getAuctionById(auctionId);
        userService.removeAuctionFromFavorites(user, auction);
    }

    public List<Auction> getFavoriteAuctionsOfUser(Long userId) {
        User user = getUserById(userId);
        return auctionService.getUpdatedAuctionsWithIsFavorite(userService.getFavoriteAuctions(user).stream()
                .map(imageService::getAuctionWithImageFiles)
                .toList(), userService.getCurrentUser());
    }
}
