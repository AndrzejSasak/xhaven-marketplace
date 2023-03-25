package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.OfferDto;
import com.xhaven.xhavenserver.model.entity.Offer;
import com.xhaven.xhavenserver.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/offers")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final ModelMapper modelMapper;

    @GetMapping("/{offerId}")
    public OfferDto getOfferById(@PathVariable Long offerId) {
        return modelMapper.map(offerService.getOfferById(offerId), OfferDto.class);
    }

    @GetMapping
    public List<OfferDto> getAllOffers() {
        return offerService.getAllOffers().stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .toList();
    }

    @PostMapping
    public void postOffer(@RequestBody Offer offer) {
        offerService.saveNewOffer(offer);
    }

}
