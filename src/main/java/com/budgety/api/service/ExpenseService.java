package com.budgety.api.service;

import com.budgety.api.entity.Expense;
import com.budgety.api.payload.expense.ExpenseDto;

import java.util.List;
import java.util.Set;

public interface ExpenseService {
    ExpenseDto createExpense(Long userId, ExpenseDto expenseDto);
    boolean deleteExpense(Long principal, Long id);
    ExpenseDto updateExpense(Long userId, Long expenseId, ExpenseDto expenseDto);
    ExpenseDto findExpenseById(Long expenseId);
    List<ExpenseDto> findAllExpensesByUser(Long userId);
    Set<Expense> getExpenseEntitiesByCategoryIds(Set<Long> categoryIds, Long userId);
}
