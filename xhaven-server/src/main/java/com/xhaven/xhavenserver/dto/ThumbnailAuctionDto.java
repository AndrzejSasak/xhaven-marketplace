package com.xhaven.xhavenserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailAuctionDto {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private ImageDto thumbnail;
    private LocalDateTime postedAt;
    private boolean isFavorite;

}
