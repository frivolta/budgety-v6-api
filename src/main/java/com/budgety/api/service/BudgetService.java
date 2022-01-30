package com.budgety.api.service;

import com.budgety.api.entity.User;
import com.budgety.api.payload.BudgetDto;

import java.util.Set;

public interface BudgetService {
    BudgetDto createBudget(BudgetDto budgetDto, Set<Long> categoryIds, Long userId);
}
