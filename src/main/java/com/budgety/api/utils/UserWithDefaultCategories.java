package com.budgety.api.utils;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.User;
import com.budgety.api.payload.category.CategoryDto;

import java.util.Set;
import java.util.stream.Collectors;

public class UserWithDefaultCategories {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user.setCategories(generateCategoryEntities(user));
        this.user = user;
    }

    private Set<Category> generateCategoryEntities(User user){
        Set<CategoryDto> categoryDtos = new DefaultCategories.Builder()
                .withDefaultExpenses()
                .withDefaultIncomes()
                .build()
                .getDefaultCategories();
        Set<Category> categories = categoryDtos.stream().map(categoryDto-> {
            Category category = new Category();
            category.setUser(user);
            category.setName(categoryDto.getName());
            category.setColor(categoryDto.getColor());
            category.setIcon(categoryDto.getIcon());
            category.setType(categoryDto.getType());
            return category;
        }).collect(Collectors.toSet());

        return categories;
    }
}
