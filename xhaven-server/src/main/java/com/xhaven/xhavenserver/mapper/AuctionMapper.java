package com.xhaven.xhavenserver.mapper;

import com.xhaven.xhavenserver.dto.ImageDto;
import com.xhaven.xhavenserver.dto.ThumbnailAuctionDto;
import com.xhaven.xhavenserver.model.entity.Auction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuctionMapper {

    private final ModelMapper modelMapper;
    public ThumbnailAuctionDto mapToThumbnail(Auction auction) {
        return ThumbnailAuctionDto.builder()
                .id(auction.getId())
                .title(auction.getTitle())
                .price(auction.getPrice())
                .thumbnail(modelMapper.map(auction.getImages().get(0), ImageDto.class))
                .postedAt(auction.getPostedAt())
                .isFavorite(auction.getIsFavorite())
                .build();
    }

}
