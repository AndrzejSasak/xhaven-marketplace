package com.xhaven.xhavenserver.model.entity;

import com.xhaven.xhavenserver.model.CategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

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
    private CategoryEnum category;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User owner;

    @Column(name = "POSTED_DATE")
    private LocalDate postedDate;

}
