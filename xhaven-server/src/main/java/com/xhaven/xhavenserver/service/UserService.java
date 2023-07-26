package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.config.security.CustomUserDetails;
import com.xhaven.xhavenserver.exception.UserNotFoundException;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getCurrentUser();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void addAuctionToFavorites(Long userId, Auction auction) {
        User user = getUserById(userId);
        user.addFavoriteAuction(auction);
        userRepository.save(user);
    }

    @Transactional
    public void removeAuctionFromFavorites(User user, Auction auction) {
        user.removeFavoriteAuction(auction);
        userRepository.save(user);
    }

    public List<Auction> getFavoriteAuctionsOfUser(Long userId) {
        return getUserById(userId).getFavoriteAuctions();
    }

    public List<User> getFollowersOfAuction(Auction auction) {
        return userRepository.findFollowersOfAuction(auction);
    }
}
