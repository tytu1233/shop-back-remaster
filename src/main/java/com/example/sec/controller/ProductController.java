package com.example.sec.controller;

import com.example.sec.dto.product.ProductDto;
import com.example.sec.model.Category;
import com.example.sec.model.Product;
import com.example.sec.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getAll(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        return productService.getAllProducts(pageable);
    }

    @PostMapping("/create")
    public Long createProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/category")
    public List<Product> getAllWithCategory() {
        return productService.getAllWithCategoryService();
    }
}
