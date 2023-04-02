package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
