package com.xhaven.xhavenserver.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AUCTION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_AUCTION")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User owner;

    @Column(name = "POSTED_AT")
    private LocalDateTime postedAt;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Transient
    private Boolean isFavorite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auction auction)) return false;
        return Objects.equals(id, auction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
