package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.model.entity.Category;
import com.xhaven.xhavenserver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //TODO add caching
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByParentCategoryEmpty();
    }

}
