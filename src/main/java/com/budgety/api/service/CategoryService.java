package com.budgety.api.service;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.CategoryType;
import com.budgety.api.entity.User;
import com.budgety.api.payload.category.CategoryDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface CategoryService {
 CategoryDto getCategoryByName(String name, Long userId);
 CategoryDto getCategoryById(Long catId, Long userId);
 CategoryDto createCategory(CategoryDto category, Long userId);
 Category getCategoryEntityById(Long id, Long userid);
 Set<Category> getCategoryEntitiesById(Set<Long> ids, Long userId);
 List<CategoryDto> getCategoryEntitiesByUserId(Long userId);
 List<CategoryDto> getCategoriesByType(Long userId, CategoryType categoryType);
 CategoryDto updateCategoryById(Long userId, Long categoryId, CategoryDto categoryDto);
 boolean deleteCategoryById(Long userId, Long categoryId);
 void generateDefaultForUser(User user);
}
