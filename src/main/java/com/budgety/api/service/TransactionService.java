package com.budgety.api.service;

import com.budgety.api.entity.Transaction;
import com.budgety.api.payload.transaction.TransactionDto;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface TransactionService {
    TransactionDto createTransaction(Long userId, TransactionDto transactionDto) throws ParseException;
    boolean deleteTransaction(Long principal, Long id);
    TransactionDto updateTransaction(Long userId, Long transactionId, TransactionDto transactionDto);
    TransactionDto findTransactionById(Long transactionId);
    List<TransactionDto> findAllTransactionsByUserId(Long userId);
    Set<Transaction> getTransactionEntitiesByCategoryIds(Set<Long> categoryIds, Long userId);
    void checkForMonthlyBudget(Long userId, Transaction transaction) throws ParseException;
}
