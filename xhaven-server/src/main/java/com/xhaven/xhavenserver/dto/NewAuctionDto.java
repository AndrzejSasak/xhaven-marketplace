package com.xhaven.xhavenserver.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NewAuctionDto {

    private String title;
    private String description;
    private BigDecimal price;
    private String contactInformation;
    private String phoneNumber;
    private CategoryDto category;

}
