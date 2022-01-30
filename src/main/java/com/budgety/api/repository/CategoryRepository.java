package com.budgety.api.repository;

import com.budgety.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndUserId(String name, Long userId);
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    @Query("select c from Category c where c.id in (:ids) and c.user.id = (:userId)")
    List<Category> findAllByIdsAndUserId(Iterable<Long> ids, Long userId);
}
