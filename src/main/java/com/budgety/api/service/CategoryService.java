package com.budgety.api.service;

import com.budgety.api.entity.Category;
import com.budgety.api.payload.CategoryDto;

public interface CategoryService {
 CategoryDto getCategoryByName(String name, Long userId);
 Category getCategoryEntityByName(String name, Long userid);
 CategoryDto createCategory(CategoryDto category, Long userId);
}
