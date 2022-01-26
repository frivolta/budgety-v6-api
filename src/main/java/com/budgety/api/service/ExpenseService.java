package com.budgety.api.service;

import com.budgety.api.payload.ExpenseDeleteRequest;
import com.budgety.api.payload.ExpenseDto;

import java.security.Principal;

public interface ExpenseService {
    ExpenseDto createExpense(Principal principal, ExpenseDto expenseDto);
    boolean deleteExpense(Long principal, Long id);
    ExpenseDto updateExpense(Principal principal, ExpenseDto expenseDto);
    ExpenseDto getExpenseById(Principal principal, Long id);
    ExpenseDto getExpensesByUser(Principal principal);
}
