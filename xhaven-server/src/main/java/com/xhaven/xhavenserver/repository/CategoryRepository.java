package com.xhaven.xhavenserver.repository;

import com.xhaven.xhavenserver.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT c FROM Category c WHERE c.parentCategory = null")
    List<Category> findAllByParentCategoryEmpty();

    Optional<Category> findCategoryById(Long id);
}
