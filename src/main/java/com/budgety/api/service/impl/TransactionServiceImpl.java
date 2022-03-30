package com.budgety.api.service.impl;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.Transaction;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.transaction.TransactionDto;
import com.budgety.api.repository.TransactionRepository;
import com.budgety.api.service.CategoryService;
import com.budgety.api.service.TransactionService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private CategoryService categoryService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserService userService, ModelMapper modelMapper,
                                  CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService=categoryService;
    }

    @Override
    public TransactionDto createTransaction(Long userId, TransactionDto transactionDto) {
        User user = userService.getUserEntityById(userId);
        Category category = categoryService.getCategoryEntityById(transactionDto.getCategoryId(), user.getId());
        Transaction transaction = mapToEntity(transactionDto);
        transaction.setUser(user);
        transaction.setCategory(category);
        Transaction newTransaction = transactionRepository.save(transaction);
        return mapToDto(newTransaction);
    }

    @Override
    public boolean deleteTransaction(Long expenseId, Long userId) {
        Transaction transaction = transactionRepository.findTransactionByIdAndUserId(expenseId, userId);
        transactionRepository.delete(transaction);
        return true;
    }

    @Override
    public TransactionDto updateTransaction(Long userId, Long expenseId, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findTransactionByIdAndUserId(expenseId, userId);
        Category category = categoryService.getCategoryEntityById(transactionDto.getCategoryId(), userId);
        transaction.setTransactionDescription(transactionDto.getTransactionDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setCategory(category);
        transaction.setTransactionType(transactionDto.getType());
        transactionRepository.save(transaction);
        return mapToDto(transaction);
    }

    @Override
    public TransactionDto findTransactionById(Long expenseId) {
        Transaction transaction = transactionRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("transaction", "expenseId", expenseId.toString()));
        return mapToDto(transaction);
    }

    @Override
    public List<TransactionDto> findAllTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByUserId(userId);
        return transactions.stream().map(transaction -> mapToDto(transaction)).collect(Collectors.toList());
    }

    @Override
    public Set<Transaction> getTransactionEntitiesByCategoryIds(Set<Long> categoryIds, Long userId) {
        List<Transaction> transactions = transactionRepository.findTransactionByCategoriesAndUserId(categoryIds, userId);
        return new HashSet<>(transactions);
    }


    private Transaction mapToEntity(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }
}
