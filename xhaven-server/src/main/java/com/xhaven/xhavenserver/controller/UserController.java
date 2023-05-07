package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.facade.UserFacade;
import com.xhaven.xhavenserver.dto.ThumbnailAuctionDto;
import com.xhaven.xhavenserver.dto.UserDto;
import com.xhaven.xhavenserver.mapper.AuctionMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final UserFacade userFacade;
    private final AuctionMapper auctionMapper;

    @GetMapping("/current")
    public UserDto getCurrentlyLoggedInUser() {
        return modelMapper.map(userFacade.getCurrentUser(), UserDto.class);
    }

    @GetMapping("/{userId}/auctions/favorites")
    public List<ThumbnailAuctionDto> getFavoriteAuctionsOfUser(@PathVariable Long userId) {
        return userFacade.getFavoriteAuctionsOfUser(userId).stream()
                .map(auctionMapper::mapToThumbnail)
                .toList();
    }

    @PostMapping("/{userId}/auctions/favorites/{auctionId}")
    public void addAuctionToFavorites(@PathVariable Long userId, @PathVariable Long auctionId) {
        userFacade.addAuctionToFavorites(userId, auctionId);
    }

    @DeleteMapping("/{userId}/auctions/favorites/{auctionId}")
    public void removeAuctionFromFavorites(@PathVariable Long userId, @PathVariable Long auctionId) {
        userFacade.removeAuctionFromFavorites(userId, auctionId);
    }

}
