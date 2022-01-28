package com.budgety.api.repository;

import com.budgety.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndUserId(String name, Long userId);
}
