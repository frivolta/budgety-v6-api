package com.budgety.api.repository;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.Expense;
import com.budgety.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense findExpenseByIdAndUserId(Long expenseId, Long userId);
    List<Expense> findExpensesByUserId(Long id);
    @Query("select e from Expense e where e.category.id in (:categoryIds) and e.user.id = (:userId)")
    List<Expense> findExpenseByCategoriesAndUserId(Iterable<Long> categoryIds, Long userId);
}
