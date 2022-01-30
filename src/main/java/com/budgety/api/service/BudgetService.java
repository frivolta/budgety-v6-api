package com.budgety.api.service;

import com.budgety.api.payload.budget.BudgetDto;

import java.util.Set;

public interface BudgetService {
    BudgetDto createBudget(BudgetDto budgetDto, Set<Long> categoryIds, Long userId);
    BudgetDto updateBudget(Long budgetId, BudgetDto budgetDto, Set<Long> categoryIds, Long userId);
    boolean deleteBudget(Long id, Long userId);
    BudgetDto getBudget(Long id, Long userId);
    Set<BudgetDto> getAllBudgets(Long userId);
}
