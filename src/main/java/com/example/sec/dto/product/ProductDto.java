package com.example.sec.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto (
        @Size(min = 3, max = 20, message = "Name must contain at least 3 characters")
        @NotNull(message = "Namecan not be null")
        String name,

        @Min(value = 1, message = "Price cannot be negative number")
        @Max(value = 99999, message = "Maximal price is 99999$")
        Double price,

        @NotNull(message = "Category has to be specified")
        Long categoryId
) {
}
