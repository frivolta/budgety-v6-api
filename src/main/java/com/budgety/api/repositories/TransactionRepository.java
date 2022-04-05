package com.budgety.api.repositories;

import com.budgety.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findTransactionByIdAndUserId(Long expenseId, Long userId);
    List<Transaction> findTransactionsByUserId(Long id);
    @Query("select e from Transaction e where e.category.id in (:categoryIds) and e.user.id = (:userId)")
    List<Transaction> findTransactionByCategoriesAndUserId(Iterable<Long> categoryIds, Long userId);
    @Query("select t from Transaction t where t.user.id =:userId and t.date between :startDate and :endDate")
    List<Transaction> findTransactionsByDateRangeAndUserId(LocalDate startDate, LocalDate endDate, Long userId);
}
