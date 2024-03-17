package com.example.sec.controller;

import com.example.sec.dto.category.CategoryDto;
import com.example.sec.model.Category;
import com.example.sec.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Long createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategoryService(categoryDto);
    }

    @DeleteMapping
    public void deleteCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.deleteCategoryService(categoryDto);
    }

    @GetMapping
    public List<Category> getAllWithProducts() {
        return categoryService.getAllWithProductsService();
    }

}
