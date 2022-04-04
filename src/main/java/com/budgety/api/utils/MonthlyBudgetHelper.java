package com.budgety.api.utils;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.EnrichedCategory;
import com.budgety.api.entity.MonthlyBudget;
import com.budgety.api.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MonthlyBudgetHelper {
    public static Set<EnrichedCategory> generateEnrichedCategoriesFromCategories(List<Category> categories, User user, MonthlyBudget monthlyBudget){
        return categories.stream().map((category)->{
            EnrichedCategory enrichedCategory = new EnrichedCategory();
            enrichedCategory.setCategory(category);
            enrichedCategory.setBudgetOrGoal(new BigDecimal(0));
            enrichedCategory.setCurrentAmount(new BigDecimal(0));
            enrichedCategory.setUser(user);
            enrichedCategory.setMonthlyBudget(monthlyBudget);
            return enrichedCategory;
        }).collect(Collectors.toSet());
    }
}
