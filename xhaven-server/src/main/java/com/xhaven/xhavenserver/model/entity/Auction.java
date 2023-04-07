package com.xhaven.xhavenserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "AUCTION")
@Getter
@Setter
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

    @Column(name = "CONTACT_INFORMATION")
    private String contactInformation;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "PRICE")
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_auction")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    @JsonIgnore
    private User owner;

    @Column(name = "POSTED_DATE_TIME")
    private LocalDateTime postedDateTime;

    private boolean isActive;

}
