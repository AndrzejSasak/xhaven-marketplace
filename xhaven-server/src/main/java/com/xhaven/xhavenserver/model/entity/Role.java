package com.xhaven.xhavenserver.model.entity;

import com.xhaven.xhavenserver.model.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }
}
