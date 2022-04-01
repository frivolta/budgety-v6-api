package com.budgety.api.controller;

import com.budgety.api.entity.CategoryType;
import com.budgety.api.payload.category.CategoryDeleteRequest;
import com.budgety.api.payload.category.CategoryDto;
import com.budgety.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getCategories(@PathVariable Long userId) {
        List<CategoryDto> categoryDtos = categoryService.getCategoryEntitiesByUserId(userId);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/id/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId
    ) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId, userId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/type/{categoryType}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByType(
            @PathVariable Long userId,
            @PathVariable CategoryType categoryType
    ) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesByType(userId, categoryType);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PutMapping("/id/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        CategoryDto updatedCategory = categoryService.updateCategoryById(userId, categoryId, categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/id/{categoryId}")
    public ResponseEntity<CategoryDeleteRequest> deleteCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId
            ) {
        boolean ok = categoryService.deleteCategoryById(userId, categoryId);
        CategoryDeleteRequest resp = new CategoryDeleteRequest();
        resp.setOk(ok);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
