package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.ChangedAuctionStatusDto;
import com.xhaven.xhavenserver.dto.CompleteAuctionDto;
import com.xhaven.xhavenserver.dto.NewAuctionDto;
import com.xhaven.xhavenserver.dto.ThumbnailAuctionDto;
import com.xhaven.xhavenserver.mapper.AuctionMapper;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.service.AuctionService;
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

    private final ModelMapper modelMapper;
    private final AuctionMapper auctionMapper;
    private final AuctionService auctionService;

    @GetMapping("/{auctionId}")
    public CompleteAuctionDto getAuctionById(@PathVariable Long auctionId) {
        return modelMapper.map(auctionService.getCompleteAuction(auctionId), CompleteAuctionDto.class);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postAuction(@RequestParam MultipartFile[] files,
                            @RequestPart NewAuctionDto newAuctionDto) {
        auctionService.saveNewAuction(modelMapper.map(newAuctionDto, Auction.class), files);
    }

    @PutMapping("/{auctionId}/status")
    public void changeAuctionStatus(@PathVariable Long auctionId,
                                    @RequestBody ChangedAuctionStatusDto changedAuctionStatusDto) {
        auctionService.changeAuctionStatus(auctionId, changedAuctionStatusDto.isActive(), changedAuctionStatusDto.isActive());
    }

    @GetMapping
    public List<ThumbnailAuctionDto> getAuctionThumbnails(@RequestParam(required = false) Long ownerId,
                                                          @RequestParam(required = false) Boolean isActive,
                                                          @RequestParam Long currentUserId) {
        return auctionService.getAuctionsWithImagesAndFavoriteFlag(currentUserId, ownerId, isActive).stream()
                .map(auctionMapper::mapToThumbnail)
                .toList();
    }

}
