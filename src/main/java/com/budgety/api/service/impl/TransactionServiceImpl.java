package com.budgety.api.service.impl;

import com.budgety.api.components.UpdaterComponent;
import com.budgety.api.entity.*;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;
import com.budgety.api.payload.transaction.TransactionDto;
import com.budgety.api.repositories.EnrichedCategoryRepository;
import com.budgety.api.repositories.MonthlyBudgetRepository;
import com.budgety.api.repositories.TransactionRepository;
import com.budgety.api.service.CategoryService;
import com.budgety.api.service.MonthlyBudgetService;
import com.budgety.api.service.TransactionService;
import com.budgety.api.service.UserService;
import com.budgety.api.utils.MonthlyBudgetHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
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
    private MonthlyBudgetRepository monthlyBudgetRepository;
    private MonthlyBudgetService monthlyBudgetService;
    private EnrichedCategoryRepository enrichedCategoryRepository;
    private UpdaterComponent updaterComponent;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserService userService, ModelMapper modelMapper,
                                  CategoryService categoryService,
                                  MonthlyBudgetRepository monthlyBudgetRepository,
                                  MonthlyBudgetService monthlyBudgetService,
                                  EnrichedCategoryRepository enrichedCategoryRepository,
                                  UpdaterComponent updaterComponent
    ) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService=categoryService;
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.monthlyBudgetService = monthlyBudgetService;
        this.enrichedCategoryRepository = enrichedCategoryRepository;
        this.updaterComponent = updaterComponent;
    }


    @Override
    @Transactional
    public void checkForMonthlyBudget(Long userId, Transaction transaction) throws ParseException {
        LocalDate tDate = transaction.getDate();
        LocalDate startDate = tDate.withDayOfMonth(1);
        LocalDate endDate = tDate.withDayOfMonth(tDate.lengthOfMonth());
        boolean isExists = monthlyBudgetRepository.existsByDateRange(tDate, userId);
        if(!isExists){
            MonthlyBudgetDto monthlyBudgetDto = new MonthlyBudgetDto();
            monthlyBudgetDto.setStartDate(startDate);
            monthlyBudgetDto.setEndDate(endDate);
            monthlyBudgetService.createMonthlyBudget(userId, monthlyBudgetDto);
        }
    }

    // Add a transaction to a monthly budget

    @Override
    public TransactionDto createTransaction(Long userId, TransactionDto transactionDto) throws ParseException {
        User user = userService.getUserEntityById(userId);
        Category category = categoryService.getCategoryEntityById(transactionDto.getCategoryId(), user.getId());
        Transaction transaction = mapToEntity(transactionDto);

        // Check if monthly budget for that period exists, otherwise create one
        checkForMonthlyBudget(userId, transaction);

        // Get monthly budget using the transaction date
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByDate(transaction.getDate(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("monthly budget", "date", transaction.getDate().toString()));

        EnrichedCategory enrichedCategory = enrichedCategoryRepository.getEnrichedCategoryByMonthlyBudgetIdAndCategoryId(
                monthlyBudget.getId(),
                category.getId()
        );
        // Create the transaction
        transaction.setUser(user);
        transaction.setCategory(category);
        // Add the transaction to the monthly budget
        transaction.setMonthlyBudget(monthlyBudget);
        // Add the transaction to the enriched category
        transaction.setEnrichedCategory(enrichedCategory);
        Transaction newTransaction = transactionRepository.save(transaction);

        updaterComponent.updateTransactionEnrichedCategory(newTransaction);
        return mapToDto(newTransaction);
    }

    @Override
    public boolean deleteTransaction(Long expenseId, Long userId) {
        Transaction transaction = transactionRepository.findTransactionByIdAndUserId(expenseId, userId);
        transactionRepository.delete(transaction);
        updaterComponent.updateTransactionEnrichedCategory(transaction);
        return true;
    }

    @Override
    @Transactional
    public TransactionDto updateTransaction(Long userId, Long expenseId, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findTransactionByIdAndUserId(expenseId, userId);
        Category category = categoryService.getCategoryEntityById(transactionDto.getCategoryId(), userId);
        transaction.setTransactionDescription(transactionDto.getTransactionDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setCategory(category);
        transaction.setTransactionType(transactionDto.getType());
        Transaction newTransaction = transactionRepository.save(transaction);
        updaterComponent.updateTransactionEnrichedCategory(newTransaction);
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
