package com.example.sec.repository;

import com.example.sec.model.Category;
import com.example.sec.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);

    List<Product> findAllByCategoryIdIn(List<Long> ids);
}
