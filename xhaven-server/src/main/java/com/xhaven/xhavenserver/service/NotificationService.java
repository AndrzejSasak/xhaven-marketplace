package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import com.xhaven.xhavenserver.dto.CompleteAuctionDto;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaTemplate<String, AuctionTakenDownNotificationDto> kafkaTemplate;
    private final ModelMapper modelMapper;

    public void sendAuctionTakenDownNotifications(Auction auction, List<User> followers) {
        kafkaTemplate.send("auction-taken-down-topic", buildNotification(auction, followers));
    }

    private AuctionTakenDownNotificationDto buildNotification(Auction auction, List<User> followers) {
        return AuctionTakenDownNotificationDto.builder()
                .completeAuctionDto(modelMapper.map(auction, CompleteAuctionDto.class))
                .recipientsEmails(followers.stream()
                        .map(User::getEmail)
                        .toList())
                .build();
    }

}

