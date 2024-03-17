package com.example.sec.mapper.category;

import com.example.sec.dto.category.CategoryDto;
import com.example.sec.model.Category;

public class CategoryMapper {

    public static CategoryDto mapCategoryToDto(Category category) {
        return new CategoryDto(category.getName());
    }

    public static Category mapDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.name())
                .build();
    }
}
