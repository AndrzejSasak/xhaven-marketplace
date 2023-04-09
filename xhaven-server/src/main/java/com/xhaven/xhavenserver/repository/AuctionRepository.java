package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findAllByOwnerId(Long ownerId);

}
