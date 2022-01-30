package com.budgety.api.service;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.User;
import com.budgety.api.payload.CategoryDto;

import java.util.Set;

public interface CategoryService {
 CategoryDto getCategoryByName(String name, Long userId);
 CategoryDto createCategory(CategoryDto category, Long userId);
 Category getCategoryEntityByName(String name, Long userid);
 Category getCategoryEntityById(Long id, Long userid);
 Set<Category> getCategoryEntitiesById(Set<Long> ids, Long userId);
 void generateDefaultForUser(User user);
}
