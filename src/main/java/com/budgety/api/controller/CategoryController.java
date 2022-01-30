package com.budgety.api.controller;

import com.budgety.api.payload.category.CategoryDto;
import com.budgety.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(
            @PathVariable Long userId,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        CategoryDto category = categoryService.createCategory(categoryDto, userId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
