package com.xhaven.xhavenserver.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Table(name = "IMAGE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_IMAGE")
    private Long id;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @Transient
    private byte[] fileBytes;

}
