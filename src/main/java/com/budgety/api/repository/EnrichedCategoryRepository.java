package com.budgety.api.repository;

import com.budgety.api.entity.EnrichedCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrichedCategoryRepository extends JpaRepository<EnrichedCategory, Long> {
    EnrichedCategory getEnrichedCategoryByIdAndUserId(Long id, Long userId);
}
