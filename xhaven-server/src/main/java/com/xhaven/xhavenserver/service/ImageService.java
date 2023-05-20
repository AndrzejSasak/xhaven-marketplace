package com.xhaven.xhavenserver.service;


import com.xhaven.xhavenserver.model.entity.Auction;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    public static final String FILENAME_SEPARATOR = "&";

    private String pathValue = System.getProperty("user.dir") + File.separator +
            "xhaven-server" + File.separator +
            "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "static" + File.separator +
            "images";
    private final Path uploads = Path.of(pathValue);

    public List<Image> saveImagesToFilesystem(MultipartFile[] images) {
        List<Image> imageEntities = new ArrayList<>();

        for(MultipartFile imageFile : images) {
            String uniqueFilename = getUniqueName(imageFile);
            saveImageToFilesystem(imageFile, uniqueFilename);

            Image imageEntity = Image.builder()
                    .imageName(uniqueFilename)
                    .build();

            imageEntities.add(imageEntity);
        }

        return imageEntities;
    }

    public Auction getAuctionWithImageFiles(Auction auction) {
        List<Image> images = auction.getImages().stream()
                .peek(image -> image
                        .setFileBytes(readBytes(new File(pathValue + File.separator + image.getImageName()))))
                .toList();
        auction.setImages(images);
        return auction;
    }

    private void saveImageToFilesystem(MultipartFile image, String filename) {
        try {
            Files.copy(image.getInputStream(), uploads.resolve(filename));
        } catch (IOException ioException) {
            throw new RuntimeException("Could not store image: " + ioException.getMessage());
        }
    }

    private byte[] readBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException ioException) {
            throw new RuntimeException("Unable to read bytes: " + ioException.getMessage());
        }
    }

    private String getUniqueName(MultipartFile image) {
        return UUID.randomUUID() + FILENAME_SEPARATOR + image.getOriginalFilename();
    }

}
