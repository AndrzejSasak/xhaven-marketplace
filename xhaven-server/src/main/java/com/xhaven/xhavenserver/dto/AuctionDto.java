package com.xhaven.xhavenserver.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AuctionDto {

    private Long id;
    private String title;
    private String description;
    private String contactInformation;
    private BigDecimal price;
    private CategoryDto category;
    private List<ImageDto> images;
    private LocalDateTime postedDate;

}
