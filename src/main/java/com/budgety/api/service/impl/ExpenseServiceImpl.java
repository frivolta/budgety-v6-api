package com.budgety.api.service.impl;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.Expense;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.ExpenseDto;
import com.budgety.api.repository.ExpenseRepository;
import com.budgety.api.service.CategoryService;
import com.budgety.api.service.ExpenseService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryService categoryService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              UserService userService, ModelMapper modelMapper,
                              CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService=categoryService;
    }

    @Override
    public ExpenseDto createExpense(Long userId, ExpenseDto expenseDto) {
        User user = userService.getUserEntityById(userId);
        Category category = categoryService.getCategoryEntityByName(expenseDto.getCategoryName(), user.getId());
        Expense expense = mapToEntity(expenseDto);
        expense.setUser(user);
        expense.setCategory(category);
        Expense newExpense = expenseRepository.save(expense);
        return mapToDto(newExpense);
    }

    @Override
    public boolean deleteExpense(Long expenseId, Long userId) {
        Expense expense = expenseRepository.findExpenseByIdAndUserId(expenseId, userId);
        expenseRepository.delete(expense);
        return true;
    }

    @Override
    public ExpenseDto updateExpense(Long userId, Long expenseId, ExpenseDto expenseDto) {
        Expense expense = expenseRepository.findExpenseByIdAndUserId(expenseId, userId);
        Category category = categoryService.getCategoryEntityByName(expenseDto.getCategoryName(), userId);
        expense.setExpenseDescription(expenseDto.getExpenseDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(category);
        expense.setType(expenseDto.getType());
        expenseRepository.save(expense);
        return mapToDto(expense);
    }

    @Override
    public ExpenseDto findExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("expense", "expenseId", expenseId.toString()));
        return mapToDto(expense);
    }

    @Override
    public List<ExpenseDto> findAllExpensesByUser(Long userId) {
        List<Expense> expenses = expenseRepository.findExpensesByUserId(userId);
        return expenses.stream().map(expense -> mapToDto(expense)).collect(Collectors.toList());
    }


    private Expense mapToEntity(ExpenseDto expenseDto) {
        return modelMapper.map(expenseDto, Expense.class);
    }

    private ExpenseDto mapToDto(Expense expense) {
        return modelMapper.map(expense, ExpenseDto.class);
    }
}
