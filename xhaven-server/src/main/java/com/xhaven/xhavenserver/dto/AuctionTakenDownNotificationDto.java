package com.xhaven.xhavenserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionTakenDownNotificationDto {

    private CompleteAuctionDto completeAuctionDto;
    private List<String> recipientsEmails;
    private String content;

}
