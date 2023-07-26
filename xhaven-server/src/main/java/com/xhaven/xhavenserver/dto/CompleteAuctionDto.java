package com.xhaven.xhavenserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteAuctionDto {

    private Long id;
    private String title;
    private String description;
    private CategoryDto category;
    private String contactInformation;
    private String phoneNumber;
    private BigDecimal price;
    private List<ImageDto> images;
    private LocalDateTime postedAt;
    private boolean isFavorite;

}
