package com.example.sec.dto.category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryDto (
        @Size(min = 3, max = 20, message = "Name must contain at least 3 characters")
        @NotNull(message = "Name can not be null")
        String name
) {
}