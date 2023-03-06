package com.xhaven.xhavenserver.model.entity;

import com.xhaven.xhavenserver.model.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TOKEN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(name = "VALUE", unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "TOKEN_TYPE")
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    public User user;
}

