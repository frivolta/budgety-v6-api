package com.budgety.api.service.impl;

import com.budgety.api.entity.Budget;
import com.budgety.api.entity.Category;
import com.budgety.api.entity.User;
import com.budgety.api.payload.BudgetDto;
import com.budgety.api.repository.BudgetRepository;

import com.budgety.api.service.BudgetService;
import com.budgety.api.service.CategoryService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImp implements BudgetService {
    private BudgetRepository budgetRepository;
    private UserService userService;
    private CategoryService categoryService;
    private ModelMapper modelMapper;


    @Autowired
    public BudgetServiceImp(BudgetRepository budgetRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public BudgetDto createBudget(BudgetDto budgetDto, Set<Long> categoryIds, Long userId) {
        // Get a set of categories
        Set<Category> categories = categoryService.getCategoryEntitiesById(categoryIds, userId);
        // Get the user by id
        User user = userService.getUserEntityById(userId);
        // Map the dto to a budget
        Budget budget = mapToEntity(budgetDto);
        // Assign categories and user to the budget
        budget.setCategories(categories);
        budget.setUser(user);
        Budget newBudget = budgetRepository.save(budget);
        // Save the budget
        return mapToDto(newBudget);
    }

    private Budget mapToEntity(BudgetDto budgetDto) {
        return modelMapper.map(budgetDto, Budget.class);
    }

    private BudgetDto mapToDto(Budget budget) {
        return modelMapper.map(budget, BudgetDto.class);
    }
}
