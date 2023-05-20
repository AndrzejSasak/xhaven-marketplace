package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.dto.AuctionDto;
import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendAuctionTakenDownMailNotification(AuctionTakenDownNotificationDto notification) {
        emailSender.send(createAuctionTakenDownMailMessage(notification));
    }

    private SimpleMailMessage createAuctionTakenDownMailMessage(AuctionTakenDownNotificationDto notification) {
        AuctionDto auctionDto = notification.getAuctionDto();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Auction inactive");
        String body = String.format("""
                Auction you have been following has been removed.
                Auction details:
                Title:
                %s
                Description:
                %s
                Price:
                %s
                We have removed this auction from your favorite auction list.
                """,
                auctionDto.getTitle(),
                auctionDto.getDescription(),
                auctionDto.getPrice());
        message.setText(body);
        message.setTo(notification.getRecipientsEmails().toArray(new String[0]));
        return message;
    }

}
