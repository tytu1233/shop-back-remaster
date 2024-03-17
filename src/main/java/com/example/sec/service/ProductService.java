package com.example.sec.service;

import com.example.sec.dto.product.ProductDto;
import com.example.sec.exceptions.NotFoundException;
import com.example.sec.mapper.product.ProductMapper;
import com.example.sec.model.Category;
import com.example.sec.model.Product;
import com.example.sec.repository.CategoryRepository;
import com.example.sec.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sec.mapper.product.ProductMapper.mapDtoToProduct;
import static com.example.sec.mapper.product.ProductMapper.mapProductToDto;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllProducts(pageable);
        return productPage;
    }

    public List<Product> getAllWithCategoryService() {
        List<Product> products = productRepository.findAll();

        List<Long> productsIds = products.stream().map(Product::getProductId).toList();

        return products;
    }
    public Long createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.categoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
        Product product = mapDtoToProduct(productDto, category);

        return productRepository.save(product).getProductId();
    }
}
