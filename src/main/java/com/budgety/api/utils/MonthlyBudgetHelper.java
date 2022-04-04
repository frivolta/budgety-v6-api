package com.budgety.api.utils;

import com.budgety.api.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MonthlyBudgetHelper {
    public static Set<EnrichedCategory> generateEnrichedCategoriesFromCategories(List<Category> categories, User user, MonthlyBudget monthlyBudget) {
        return categories.stream().map((category) -> {
            EnrichedCategory enrichedCategory = new EnrichedCategory();
            enrichedCategory.setCategory(category);
            enrichedCategory.setType(category.getType());
            enrichedCategory.setBudgetOrGoal(new BigDecimal(0));
            enrichedCategory.setCurrentAmount(new BigDecimal(0));
            enrichedCategory.setUser(user);
            enrichedCategory.setMonthlyBudget(monthlyBudget);
            return enrichedCategory;
        }).collect(Collectors.toSet());
    }

    public static Set<Transaction> addMonthlyBudgetToTransactions(List<Transaction> transactions, MonthlyBudget monthlyBudget) {
        return transactions.stream().map(transaction -> {
            transaction.setMonthlyBudget(monthlyBudget);
            return transaction;
        }).collect(Collectors.toSet());
    }

    public static Set<Transaction> addEnrichedCategoriesToTransactions(Set<Transaction> transactions, Set<EnrichedCategory> enrichedCategories){
        return transactions.stream().map(transaction -> {
            enrichedCategories.forEach(enrichedCategory -> {
                if(enrichedCategory.getCategory().getId() == transaction.getCategory().getId()){
                    transaction.setEnrichedCategory(enrichedCategory);
                }
            });
            return transaction;
        }).collect(Collectors.toSet());
    }

    public static BigDecimal calculateInitialTransactionsTotalAmount(Set<Transaction> transactions){
       BigDecimal totalAmount = new BigDecimal(0);
       transactions.forEach(transaction -> {
           if(transaction.getTransactionType()==CategoryType.EXPENSE){
               totalAmount.subtract(transaction.getAmount());
           }
           if(transaction.getTransactionType()==CategoryType.INCOME) {
               totalAmount.add(transaction.getAmount());
           }
       });
       return totalAmount;
    }

}
