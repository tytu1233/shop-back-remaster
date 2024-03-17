package com.example.sec.service;

import com.example.sec.dto.category.CategoryDto;
import com.example.sec.exceptions.NotFoundException;
import com.example.sec.mapper.category.CategoryMapper;
import com.example.sec.model.Category;
import com.example.sec.model.Product;
import com.example.sec.repository.CategoryRepository;
import com.example.sec.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    public Long createCategoryService(CategoryDto categoryDto) {

        Category categoryToSave = CategoryMapper.mapDtoToCategory(categoryDto);

        Category category = categoryRepository.save(categoryToSave);

        return category.getCategoryId();
    }

    public List<Category> getAllWithProductsService() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Long> categoryIds = categoryList.stream().map(Category::getCategoryId).toList();
        List<Product> productList = productRepository.findAllByCategoryIdIn(categoryIds);

        categoryList.forEach(category -> category.setProducts(extractProducts(productList, category.getCategoryId())));
        return categoryList;
    }

    private List<Product> extractProducts(List<Product> products, Long id) {
        return products.stream().filter(product -> product.getCategoryId().equals(id)).toList();
    }
    public void deleteCategoryService(CategoryDto categoryDto) {

        Category category = categoryRepository.findByName(categoryDto.name()).orElseThrow(() -> new NotFoundException("Category not found"));

        categoryRepository.delete(category);
    }
}
