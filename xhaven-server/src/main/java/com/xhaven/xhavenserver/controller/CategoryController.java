package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.CategoryDto;
import com.xhaven.xhavenserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

}
