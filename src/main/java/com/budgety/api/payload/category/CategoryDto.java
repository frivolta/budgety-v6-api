package com.budgety.api.payload.category;

import com.budgety.api.entity.CategoryType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty
    @Length(min = 3, max = 50, message = "Category slug cannot be empty")
    private String slug;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryType type;
}
