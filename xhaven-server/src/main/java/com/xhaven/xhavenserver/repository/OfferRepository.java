package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
