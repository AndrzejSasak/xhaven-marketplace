package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
