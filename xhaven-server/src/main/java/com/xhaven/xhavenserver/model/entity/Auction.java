package com.xhaven.xhavenserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "AUCTION")
@Getter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_AUCTION")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    private Category category;

    @Column(name = "PRICE")
    private BigDecimal price;

    @OneToMany
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    @JsonIgnore
    private User owner;

    @Column(name = "POSTED_DATE")
    private LocalDate postedDate;

}
