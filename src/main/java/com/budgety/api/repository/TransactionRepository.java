package com.budgety.api.repository;

import com.budgety.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findTransactionByIdAndUserId(Long expenseId, Long userId);
    List<Transaction> findTransactionsByUserId(Long id);
    @Query("select e from Transaction e where e.category.id in (:categoryIds) and e.user.id = (:userId)")
    List<Transaction> findTransactionByCategoriesAndUserId(Iterable<Long> categoryIds, Long userId);
}
