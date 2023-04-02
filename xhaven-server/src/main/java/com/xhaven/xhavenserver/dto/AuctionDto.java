package com.xhaven.xhavenserver.dto;

import com.xhaven.xhavenserver.model.CategoryEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AuctionDto {

    private Long id;
    private String title;
    private String description;
    private CategoryEnum category;
    private BigDecimal price;
    private List<ImageDto> images;
    private LocalDate postedDate;

}
