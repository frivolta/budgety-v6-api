package com.budgety.api.utils;

import com.budgety.api.entity.CategoryType;
import com.budgety.api.payload.category.CategoryDto;

import java.util.HashSet;
import java.util.Set;

public class DefaultCategories {
    private final Set<CategoryDto> defaultCategories;

    private DefaultCategories(Builder builder) {
        this.defaultCategories = builder.defaultCategories;
    }

    public Set<CategoryDto> getDefaultCategories() {
        return defaultCategories;
    }

    public static class Builder {
        private final Set<CategoryDto> defaultCategories = new HashSet<>();

        private static CategoryDto generateDtoWith(String name, String color, String icon, String slug, CategoryType type) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(name);
            categoryDto.setSlug(slug);
            categoryDto.setColor(color);
            categoryDto.setIcon(icon);
            categoryDto.setType(type);
            return categoryDto;
        }

        public Builder withDefaultExpenses() {
            defaultCategories.add(generateDtoWith("Car", "#04f", "car", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Rent", "#04f", "rent", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Gym", "#04f", "gym", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Food", "#04f", "food", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Internet", "#04f", "internet", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Water", "#04f", "water", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Groceries", "#04f", "groceries", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Medical", "#04f", "medical", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Fuel", "#04f", "fuel", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Electricity", "#04f", "electricity", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Gas", "#04f", "gas", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Eating out", "#04f", "eating_out", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Takeaways", "#04f", "takeaways", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("For fun", "#04f", "for_fun", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Cell phone", "#04f", "cell_phone", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Baby", "#04f", "baby", "FireOutlined", CategoryType.EXPENSE));
            defaultCategories.add(generateDtoWith("Yoga", "#04f", "yoga", "FireOutlined", CategoryType.EXPENSE));
            return this;
        }

        public Builder withDefaultIncomes() {
            defaultCategories.add(generateDtoWith("Salary", "#a0d911", "salary", "FireOutlined", CategoryType.INCOME));
            defaultCategories.add(generateDtoWith("Side Business", "#a0d911", "side_business", "FireOutlined", CategoryType.INCOME));
            defaultCategories.add(generateDtoWith("Dividends", "#a0d911", "dividends", "FireOutlined", CategoryType.INCOME));
            defaultCategories.add(generateDtoWith("Rental Property", "#a0d911", "rental_property", "FireOutlined", CategoryType.INCOME));
            return this;
        }

        public Builder withDefaultSavings() {
            return this;
        }

        public DefaultCategories build() {
            return new DefaultCategories(this);
        }
    }
}
