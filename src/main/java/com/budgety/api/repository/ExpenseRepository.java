package com.budgety.api.repository;

import com.budgety.api.entity.Expense;
import com.budgety.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense findExpenseByIdAndUserId(Long expenseId, Long userId);
    List<Expense> findExpensesByUserId(Long id);
}
