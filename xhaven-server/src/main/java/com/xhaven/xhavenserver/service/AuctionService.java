package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Auction getAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found"));
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public void saveNewAuction(Auction auction) {
        auctionRepository.save(auction);
    }
}
