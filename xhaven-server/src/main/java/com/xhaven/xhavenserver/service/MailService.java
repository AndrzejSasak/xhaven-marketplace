package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.dto.CompleteAuctionDto;
import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String MAIL_SUBJECT = "Auction inactive";
    private final JavaMailSender emailSender;

    public void sendAuctionTakenDownMailNotification(AuctionTakenDownNotificationDto notification) {
        emailSender.send(createAuctionTakenDownMailMessage(notification));
    }

    private SimpleMailMessage createAuctionTakenDownMailMessage(AuctionTakenDownNotificationDto notification) {
        CompleteAuctionDto completeAuctionDto = notification.getCompleteAuctionDto();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(MAIL_SUBJECT);
        String body = getBody(completeAuctionDto);
        message.setText(body);
        message.setTo(notification.getRecipientsEmails().toArray(new String[0]));
        return message;
    }

    private String getBody(CompleteAuctionDto completeAuctionDto) {
        return String.format("""
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
                completeAuctionDto.getTitle(),
                completeAuctionDto.getDescription(),
                completeAuctionDto.getPrice());
    }

}
