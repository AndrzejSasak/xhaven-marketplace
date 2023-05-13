package com.xhaven.xhavenserver.dto;

import lombok.Data;

@Data
public class ChangedAuctionStatusDto {
    private boolean isActive;
    private boolean isSold;
}
