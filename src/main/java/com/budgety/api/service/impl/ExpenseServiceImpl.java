package com.budgety.api.service.impl;

import com.budgety.api.entity.Expense;
import com.budgety.api.entity.User;
import com.budgety.api.payload.ExpenseDeleteRequest;
import com.budgety.api.payload.ExpenseDto;
import com.budgety.api.payload.UserDto;
import com.budgety.api.repository.ExpenseRepository;
import com.budgety.api.repository.UserRepository;
import com.budgety.api.service.ExpenseService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserService userService, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public ExpenseDto createExpense(Principal principal, ExpenseDto expenseDto) {
        User user = userService.getUserEntity(principal.getName());
        Expense expense = mapToEntity(expenseDto);
        expense.setUser(user);
        Expense newExpense = expenseRepository.save(expense);
        return  mapToDto(newExpense);
    }

    @Override
    public boolean deleteExpense(Long expenseId, Long userId) {
        Expense expense = expenseRepository.findExpenseByIdAndUserId(expenseId, userId);
        expenseRepository.delete(expense);
        return true;
    }

    @Override
    public ExpenseDto updateExpense(Principal principal, ExpenseDto expenseDto) {
        return null;
    }

    @Override
    public ExpenseDto getExpenseById(Principal principal, Long id) {
        return null;
    }

    @Override
    public ExpenseDto getExpensesByUser(Principal principal) {
        return null;
    }

    private Expense mapToEntity(ExpenseDto expenseDto) {
        return modelMapper.map(expenseDto, Expense.class);
    }

    private ExpenseDto mapToDto(Expense expense) {
        return modelMapper.map(expense, ExpenseDto.class);
    }
}
