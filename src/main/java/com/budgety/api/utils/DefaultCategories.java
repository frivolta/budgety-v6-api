package com.budgety.api.utils;

import com.budgety.api.entity.ExpenseType;
import com.budgety.api.payload.CategoryDto;

import java.util.HashSet;
import java.util.Set;

public class DefaultCategories {
    private Set<CategoryDto> defaultCategories;

    private DefaultCategories(Builder builder){
        this.defaultCategories = builder.defaultCategories;
    }

    public Set<CategoryDto> getDefaultCategories(){
        return defaultCategories;
    }

    public static class Builder{
        private final Set<CategoryDto> defaultCategories = new HashSet<>();
        public Builder withDefaultExpenses(){
            defaultCategories.add(generateDtoWith("Food", "#000000", "icon.svg", ExpenseType.EXPENSE));
            defaultCategories.add(generateDtoWith("Insurance", "#000000", "icon.svg", ExpenseType.EXPENSE));
            defaultCategories.add(generateDtoWith("Groceries", "#000000", "icon.svg", ExpenseType.EXPENSE));
            defaultCategories.add(generateDtoWith("Bills", "#000000", "icon.svg", ExpenseType.EXPENSE));
            defaultCategories.add(generateDtoWith("Transport", "#000000", "icon.svg", ExpenseType.EXPENSE));
            return this;
        }

        public Builder withDefaultIncomes(){
            defaultCategories.add(generateDtoWith("Salary", "#000000", "icon.svg", ExpenseType.INCOME));
            defaultCategories.add(generateDtoWith("Extra", "#000000", "icon.svg", ExpenseType.INCOME));
            return this;
        }

        public DefaultCategories build(){
            return new DefaultCategories(this);
        }

        private static CategoryDto generateDtoWith(String name, String color, String icon, ExpenseType type){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(name);
            categoryDto.setColor(color);
            categoryDto.setIcon(icon);
            categoryDto.setType(type);
            return categoryDto;
        }
    }
}
