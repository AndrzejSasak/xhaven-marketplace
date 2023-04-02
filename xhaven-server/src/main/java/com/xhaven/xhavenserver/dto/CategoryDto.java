package com.xhaven.xhavenserver.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private String categoryName;
    private List<CategoryDto> subcategories;

}
