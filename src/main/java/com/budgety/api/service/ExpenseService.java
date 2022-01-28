package com.budgety.api.service;

import com.budgety.api.payload.ExpenseDeleteRequest;
import com.budgety.api.payload.ExpenseDto;

import java.security.Principal;
import java.util.List;

public interface ExpenseService {
    ExpenseDto createExpense(Long userId, ExpenseDto expenseDto);
    boolean deleteExpense(Long principal, Long id);
    ExpenseDto updateExpense(Long userId, Long expenseId, ExpenseDto expenseDto);
    ExpenseDto findExpenseById(Long expenseId);
    List<ExpenseDto> findAllExpensesByUser(Long userId);
}
