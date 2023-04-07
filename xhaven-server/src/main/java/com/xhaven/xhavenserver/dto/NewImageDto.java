package com.xhaven.xhavenserver.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewImageDto {

    private String fileName;
    private MultipartFile file;

}
