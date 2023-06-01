package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.AuctionDto;
import com.xhaven.xhavenserver.dto.ChangedAuctionStatusDto;
import com.xhaven.xhavenserver.dto.NewAuctionDto;
import com.xhaven.xhavenserver.dto.ThumbnailAuctionDto;
import com.xhaven.xhavenserver.facade.AuctionFacade;
import com.xhaven.xhavenserver.mapper.AuctionMapper;
import com.xhaven.xhavenserver.model.entity.Auction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auctions")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionFacade auctionFacade;
    private final ModelMapper modelMapper;
    private final AuctionMapper auctionMapper;

    @GetMapping("/{auctionId}")
    public AuctionDto getAuctionById(@PathVariable Long auctionId) {
        return modelMapper.map(auctionFacade.getAuctionById(auctionId), AuctionDto.class);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postAuction(@RequestParam MultipartFile[] files, @RequestPart NewAuctionDto newAuctionDto) {
        auctionFacade.saveNewAuction(modelMapper.map(newAuctionDto, Auction.class), files);
    }

    @PutMapping("/{auctionId}/status")
    public void changeAuctionStatus(@PathVariable Long auctionId, @RequestBody ChangedAuctionStatusDto changedAuctionStatusDto) {
        auctionFacade.changeAuctionStatus(auctionId, changedAuctionStatusDto.isActive(), changedAuctionStatusDto.isActive());
    }

    @GetMapping
    public List<ThumbnailAuctionDto> getAuctions(@RequestParam(required = false) Long ownerId,
                                                 @RequestParam(required = false) Boolean isActive) {
        return auctionFacade.getAuctions(ownerId, isActive).stream()
                .map(auctionMapper::mapToThumbnail)
                .toList();
    }

}
