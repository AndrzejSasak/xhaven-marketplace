package com.xhaven.xhavenserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xhaven.xhavenserver.model.CategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "OFFER")
@Getter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_OFFER")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private CategoryEnum category;

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
