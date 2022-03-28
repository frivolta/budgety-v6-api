package com.budgety.api.service.impl;

import com.budgety.api.entity.*;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.budget.BudgetDto;
import com.budgety.api.repository.BudgetRepository;

import com.budgety.api.service.BudgetService;
import com.budgety.api.service.CategoryService;
import com.budgety.api.service.ExpenseService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static com.budgety.api.entity.CategoryType.*;

@Service
public class BudgetServiceImp implements BudgetService {
    private BudgetRepository budgetRepository;
    private UserService userService;
    private CategoryService categoryService;
    private ExpenseService expenseService;
    private ModelMapper modelMapper;


    @Autowired
    public BudgetServiceImp(BudgetRepository budgetRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper, ExpenseService expenseService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.expenseService = expenseService;
    }

    @Override
    public BudgetDto createBudget(BudgetDto budgetDto, Set<Long> categoryIds, Long userId) {
        // Get a set of categories
        Set<Category> categories = categoryService.getCategoryEntitiesById(categoryIds, userId);
        Set<Expense> expenses = expenseService.getExpenseEntitiesByCategoryIds(categoryIds, userId);
        // Get the user by id
        User user = userService.getUserEntityById(userId);
        // Map the dto to a budget
        Budget budget = mapToEntity(budgetDto);
        // Assign categories and user to the budget
        budget.setLeftAmount(calculateLeftAmount(expenses, budget.getMaxAmount()));
        budget.setCategories(categories);
        budget.setExpenses(expenses);
        budget.setUser(user);
        Budget newBudget = budgetRepository.save(budget);
        // Save the budget
        return mapToDto(newBudget);
    }

    @Override
    public BudgetDto updateBudget(Long budgetId, BudgetDto budgetDto, Set<Long> categoryIds, Long userId) {
        // Get the budget with budgetId and userId
        Budget originalBudget = budgetRepository.findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("budget", "id", budgetId.toString()));
        // Map the dto to a budget
        Budget updateBudget = mapToEntity(budgetDto);
        // Get a set of categories
        Set<Category> categories = categoryService.getCategoryEntitiesById(categoryIds, userId);
        // Get a set of expenses
        Set<Expense> expenses = expenseService.getExpenseEntitiesByCategoryIds(categoryIds, userId);
        // Update relevant budget data
        originalBudget.setExpenses(expenses);
        originalBudget.setCategories(categories);
        originalBudget.setLeftAmount(calculateLeftAmount(expenses, updateBudget.getMaxAmount()));
        originalBudget.setName(updateBudget.getName());
        originalBudget.setMaxAmount(updateBudget.getMaxAmount());
        originalBudget.setFromDate(updateBudget.getFromDate());
        originalBudget.setToDate(updateBudget.getToDate());
        // Save the budget
        budgetRepository.save(originalBudget);
        return mapToDto(originalBudget);
    }

    @Override
    public boolean deleteBudget(Long id, Long userId) {
        Budget budget = budgetRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("budget", "id", id.toString()));
        budgetRepository.delete(budget);
        return true;
    }

    @Override
    public BudgetDto getBudget(Long id, Long userId) {
        Budget budget = budgetRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("budget", "id", id.toString()));
        return mapToDto(budget);
    }

    @Override
    public Set<BudgetDto> getAllBudgets(Long userId) {
        return budgetRepository.findAllByUserId(userId)
                .stream().map(b -> mapToDto(b)).collect(Collectors.toSet());
    }

    private BigDecimal calculateLeftAmount(Set<Expense> expenses, BigDecimal initialAmount) {
        BigDecimal expensesSum = new BigDecimal(0);
        /////////
        return initialAmount.subtract(expensesSum);
    }

    private Budget mapToEntity(BudgetDto budgetDto) {
        return modelMapper.map(budgetDto, Budget.class);
    }

    private BudgetDto mapToDto(Budget budget) {
        return modelMapper.map(budget, BudgetDto.class);
    }
}
