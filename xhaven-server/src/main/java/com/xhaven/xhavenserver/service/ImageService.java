package com.xhaven.xhavenserver.service;


import com.xhaven.xhavenserver.model.entity.Image;
import com.xhaven.xhavenserver.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final Path uploads = Path.of(
            System.getProperty("user.dir") + File.separator +
                    "xhaven-server" + File.separator +
                    "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "static" + File.separator +
                    "images");

    public List<Image> createImageEntites(MultipartFile[] images) {
        List<Image> imageEntityList = new ArrayList<>();
        Arrays.stream(images).forEach(image -> imageEntityList.add(Image.builder()
                .imagePath(image.getOriginalFilename())
                .build()));

        return imageEntityList;
    }

    public void saveImagesToFilesystem(MultipartFile[] images) {
        Arrays.stream(images).forEach(this::saveImage);
    }

    private void saveImage(MultipartFile image) {
        try {
            Files.copy(image.getInputStream(), uploads.resolve(image.getOriginalFilename()));
        } catch (IOException ioException) {
            throw new RuntimeException("Could not store image: " + ioException.getMessage());
        }

    }

}
