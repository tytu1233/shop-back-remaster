package com.example.sec.mapper.product;

import com.example.sec.dto.product.ProductDto;
import com.example.sec.model.Category;
import com.example.sec.model.Product;

public class ProductMapper {

    public static ProductDto mapProductToDto(Product product) {
        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getCategoryId()
        );
    }

    public static Product mapDtoToProduct(ProductDto productDto, Category category) {
        return Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .categoryId(category.getCategoryId())
                .build();
    }
}
