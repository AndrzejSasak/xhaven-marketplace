package com.xhaven.xhavenserver.kafka;

import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import com.xhaven.xhavenserver.service.AuctionService;
import com.xhaven.xhavenserver.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final AuctionService auctionService;
    private final MailService mailService;

    @KafkaListener(
            topics = "auction-taken-down-topic",
            groupId = "auction-followers-group"
    )
    void listener(AuctionTakenDownNotificationDto notification) {
        mailService.sendAuctionTakenDownMailNotification(notification);
    }

}
