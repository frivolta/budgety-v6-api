package com.budgety.api.service;

import com.budgety.api.entity.EnrichedCategory;
import com.budgety.api.payload.enrichedCategory.EnrichedCategoryDto;

public interface EnrichedCategoryService {
    EnrichedCategoryDto createEnrichedCategoryInBudget(Long userId, Long budgetId, EnrichedCategoryDto enrichedCategoryDto);
    EnrichedCategoryDto updateEnrichedCategoryInBudget(Long userId, Long EnrichedCategoryId, EnrichedCategoryDto enrichedCategoryDto);
    EnrichedCategoryDto getEnrichedCategoryById(Long userId, Long EnrichedCategoryId);
    boolean deleteEnrichedCategory(Long userId, Long EnrichedCategoryId);

}
