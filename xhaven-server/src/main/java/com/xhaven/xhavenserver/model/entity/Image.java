package com.xhaven.xhavenserver.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "IMAGE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imagePath;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_auction")
//    private Auction auction;

}
