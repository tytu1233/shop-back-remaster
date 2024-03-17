package com.example.sec.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "categoryId")
    private List<Product> products;
}
