package com.budgety.api.service.impl;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.EnrichedCategory;
import com.budgety.api.entity.MonthlyBudget;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.enrichedCategory.EnrichedCategoryDto;
import com.budgety.api.repositories.CategoryRepository;
import com.budgety.api.repositories.EnrichedCategoryRepository;
import com.budgety.api.repositories.MonthlyBudgetRepository;
import com.budgety.api.repositories.UserRepository;
import com.budgety.api.service.EnrichedCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EnrichedCategoryServiceImpl implements EnrichedCategoryService {
    private MonthlyBudgetRepository monthlyBudgetRepository;
    private ModelMapper modelMapper;
    private EnrichedCategoryRepository enrichedCategoryRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public EnrichedCategoryServiceImpl( UserRepository userRepository,CategoryRepository categoryRepository, MonthlyBudgetRepository monthlyBudgetRepository, ModelMapper modelMapper, EnrichedCategoryRepository enrichedCategoryRepository) {
        this.modelMapper = modelMapper;
        this.enrichedCategoryRepository = enrichedCategoryRepository;
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    private boolean isEnrichedCategoryAlreadyInBudget(MonthlyBudget monthlyBudget, Category category){
        return monthlyBudget.getEnrichedCategories().stream().anyMatch(enrichedCategory -> enrichedCategory.getCategory().getId() == category.getId());
    }

    @Override
    @Transactional
    public EnrichedCategoryDto createEnrichedCategoryInBudget(Long userId, Long budgetId, EnrichedCategoryDto enrichedCategoryDto) {

        MonthlyBudget monthlyBudget = monthlyBudgetRepository.findByIdAnUserId(userId, budgetId)
                .orElseThrow(()->new ResourceNotFoundException("monthly budget", "id", budgetId.toString()));

        Category category = categoryRepository.findByIdAndUserId(enrichedCategoryDto.getCategoryId(), userId)
                .orElseThrow(()->new ResourceNotFoundException("category", "id", enrichedCategoryDto.getCategoryId().toString()));

        // Check if in the given monthly budget an enriched category with the same category is already present
        boolean isAlreadyInMonthlyBudget = isEnrichedCategoryAlreadyInBudget(monthlyBudget, category);
        if(isAlreadyInMonthlyBudget){
            throw new IllegalArgumentException("Enriched category with the same category already present in monthly budget");
        }
        User user = userRepository.getById(userId);
        EnrichedCategory enrichedCategory = mapToEntity(enrichedCategoryDto);
        enrichedCategory.setMonthlyBudget(monthlyBudget);
        enrichedCategory.setUser(user);
        enrichedCategory.setCategory(category);
        enrichedCategoryRepository.save(enrichedCategory);
        return enrichedCategoryDto;
    }

    @Override
    public EnrichedCategoryDto updateEnrichedCategoryInBudget(Long userId, Long enrichedCategoryId, EnrichedCategoryDto enrichedCategoryDto) {
        EnrichedCategory oldEnrichedCategory = enrichedCategoryRepository.getEnrichedCategoryByIdAndUserId(enrichedCategoryId, userId);
        oldEnrichedCategory.setBudgetOrGoal(enrichedCategoryDto.getBudgetOrGoal());
        EnrichedCategory newEnrichedCategory = enrichedCategoryRepository.save(oldEnrichedCategory);
        return mapToDto(newEnrichedCategory);
    }



    @Override
    public EnrichedCategoryDto getEnrichedCategoryById(Long userId, Long enrichedCategoryId) {
        EnrichedCategory enrichedCategory = enrichedCategoryRepository.getEnrichedCategoryByIdAndUserId(enrichedCategoryId, userId);
        return mapToDto(enrichedCategory);
    }


    @Override
    public boolean deleteEnrichedCategory(Long userId, Long enrichedCategoryId) {
        EnrichedCategory enrichedCategory = enrichedCategoryRepository.getEnrichedCategoryByIdAndUserId(enrichedCategoryId, userId);
        enrichedCategoryRepository.delete(enrichedCategory);
        return true;
    }

    private EnrichedCategoryDto mapToDto(EnrichedCategory enrichedCategory){
        return modelMapper.map(enrichedCategory, EnrichedCategoryDto.class);
    }

    private EnrichedCategory mapToEntity(EnrichedCategoryDto enrichedCategoryDto){
        return modelMapper.map(enrichedCategoryDto, EnrichedCategory.class);
    }
}
