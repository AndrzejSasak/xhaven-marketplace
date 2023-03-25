package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.model.entity.Offer;
import com.xhaven.xhavenserver.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public Offer getOfferById(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public void saveNewOffer(Offer offer) {
        offerRepository.save(offer);
    }
}
