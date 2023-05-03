package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.AuctionDto;
import com.xhaven.xhavenserver.dto.NewAuctionDto;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auctions")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final ModelMapper modelMapper;

    @GetMapping("/{auctionId}")
    public AuctionDto getAuctionById(@PathVariable Long auctionId) {
        return modelMapper.map(auctionService.getAuctionById(auctionId), AuctionDto.class);
    }

    @GetMapping
    public List<AuctionDto> getAuctions(@RequestParam Optional<Long> userId) {
        return auctionService.getAuctions(userId).stream()
                .map(auction -> modelMapper.map(auction, AuctionDto.class))
                .toList();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postAuction(@RequestParam MultipartFile[] files, @RequestPart NewAuctionDto newAuctionDto) {
        auctionService.saveNewAuction(files, modelMapper.map(newAuctionDto, Auction.class));
    }

}
