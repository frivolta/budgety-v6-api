package com.budgety.api.service.impl;

import com.budgety.api.entity.*;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;
import com.budgety.api.repositories.CategoryRepository;
import com.budgety.api.repositories.EnrichedCategoryRepository;
import com.budgety.api.repositories.MonthlyBudgetRepository;
import com.budgety.api.repositories.TransactionRepository;
import com.budgety.api.service.MonthlyBudgetService;
import com.budgety.api.service.UserService;
import com.budgety.api.utils.MonthlyBudgetHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {
    private MonthlyBudgetRepository monthlyBudgetRepository;
    private UserService userService;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;
    private EnrichedCategoryRepository enrichedCategoryRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public MonthlyBudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository,TransactionRepository transactionRepository, EnrichedCategoryRepository enrichedCategoryRepository,CategoryRepository categoryRepository, UserService userService, ModelMapper modelMapper) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.enrichedCategoryRepository = enrichedCategoryRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public MonthlyBudgetDto createMonthlyBudget(Long userId, MonthlyBudgetDto monthlyBudgetDto) {
        try {
            // Check if monthly budget is already created
            MonthlyBudget monthlyBudget = mapToEntity(monthlyBudgetDto);
            if (monthlyBudgetRepository.existsByDate(monthlyBudget.getStartDate(), monthlyBudget.getEndDate(), userId)) {
                throw new IllegalArgumentException("A budget already exists for this month");
            }

            // Get user
            User user = userService.getUserEntityById(userId);
            monthlyBudget.setUser(user);
            MonthlyBudget savedMonthlyBudget = monthlyBudgetRepository.save(monthlyBudget);

            //Create enriched categories for all categories
            List<Category> categories = categoryRepository.findAllByUserId(userId);
            Set<EnrichedCategory> enrichedCategories =
                    MonthlyBudgetHelper.generateEnrichedCategoriesFromCategories(categories, user, monthlyBudget);
            Set<EnrichedCategory> savedEnrichedCategories = enrichedCategoryRepository.saveAll(enrichedCategories).stream().collect(Collectors.toSet());

            // Get already defined transactions and insert them into monthly budget
            List<Transaction> transactions = transactionRepository
                    .findTransactionsByDateRangeAndUserId(monthlyBudget.getStartDate(), monthlyBudget.getEndDate(), userId);
            Set<Transaction> transactionsWithBudget = MonthlyBudgetHelper.addMonthlyBudgetToTransactions(transactions, monthlyBudget);
            Set<Transaction> transactionsWithBudgetAndEnrichedCategories = MonthlyBudgetHelper.addEnrichedCategoriesToTransactions(transactionsWithBudget, savedEnrichedCategories);
            transactionRepository.saveAll(transactionsWithBudgetAndEnrichedCategories);

            // return the monthly budget
            return mapToDto(savedMonthlyBudget);
        }catch(ParseException  e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public MonthlyBudgetDto getMonthlyBudget(Long userId, Long budgetId) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));
        return mapToDto(monthlyBudget);
    }

    @Override
    public Set<MonthlyBudgetDto> getMonthlyBudgets(Long userId) {
        List<MonthlyBudget> monthlyBudgets = monthlyBudgetRepository.findAllByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budgets", "userId", userId.toString()));

        return monthlyBudgets.stream().map(monthlyBudget -> mapToDto(monthlyBudget))
                .collect(Collectors.toSet());
    }

    @Override
    public MonthlyBudgetDto getMonthlyBudgetByDate(Long userId, LocalDate date) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByDate(date, userId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "date", date.toString()));
        return mapToDto(monthlyBudget);
    }

    @Override
    public boolean deleteMonthlyBudget(Long userId, Long budgetId) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));
        monthlyBudgetRepository.delete(monthlyBudget);
        return true;
    }

    // Field to update???
    @Override
    public MonthlyBudgetDto updateMonthlyBudget(Long userId, Long budgetId, MonthlyBudgetDto monthlyBudgetDto) {
        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));
        return mapToDto(monthlyBudget);
    }

    public MonthlyBudget mapToEntity(MonthlyBudgetDto monthlyBudgetDto) throws ParseException {
       MonthlyBudget entity = modelMapper.map(monthlyBudgetDto, MonthlyBudget.class);
       System.out.println(entity.toString());
       entity.setStartDate(monthlyBudgetDto.getStartDate());
       entity.setEndDate(monthlyBudgetDto.getEndDate());
       return entity;
    }

    public MonthlyBudgetDto mapToDto(MonthlyBudget monthlyBudget) {
        MonthlyBudgetDto dto = modelMapper.map(monthlyBudget, MonthlyBudgetDto.class);
        dto.setStartDate(monthlyBudget.getStartDate());
        dto.setEndDate(monthlyBudget.getEndDate());
        return dto;
    }

}
