package com.xhaven.xhavenserver.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDto {

    private Long id;
    private String imagePath;
    private MultipartFile file;

}
