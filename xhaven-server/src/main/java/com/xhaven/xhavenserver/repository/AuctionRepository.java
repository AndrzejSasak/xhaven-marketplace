package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long>,
        JpaSpecificationExecutor<Auction> {

    List<Auction> findAllByOwnerId(Long ownerId);

}
