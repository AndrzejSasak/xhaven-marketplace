package com.xhaven.xhavenserver.model.entity;

import jakarta.persistence.*;

@Table(name = "IMAGE")
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imagePath;

}
