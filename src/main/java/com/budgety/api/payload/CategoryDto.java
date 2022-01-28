package com.budgety.api.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDto {
    private Long id;
    @NotEmpty
    @Length(min = 3, max = 50, message = "Category name should be between 3 and 50 characters")
    private String name;
    @NotEmpty
    @Length(min = 3, max = 10, message = "Category color should be between 3 and 10 characters")
    private String color;
    @NotEmpty
    @Length(min = 3, max = 50, message = "Category icon should be between 3 and 50 characters")
    private String icon;
}
