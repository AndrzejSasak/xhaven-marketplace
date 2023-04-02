package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.AuctionDto;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public List<AuctionDto> getAllAuctions() {
        return auctionService.getAllAuctions().stream()
                .map(auction -> modelMapper.map(auction, AuctionDto.class))
                .toList();
    }

    @PostMapping
    public void postAuction(@RequestBody Auction auction) {
        auctionService.saveNewAuction(auction);
    }

}
