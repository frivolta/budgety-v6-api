package com.budgety.api.repository;

import com.budgety.api.entity.MonthlyBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget,Long> {
    @Query("select m from MonthlyBudget  m where m.user.id = :userId")
    Optional<List<MonthlyBudget>> findAllByUserId(Long userId);
    @Query("select m from MonthlyBudget m where m.id=:budgetId and m.user.id = :userId")
    Optional<MonthlyBudget> findByIdAnUserId(Long userId, Long budgetId);
    @Query("select m from MonthlyBudget m where m.user.id=:userId and m.startDate <=:date and m.endDate>=:date")
    Optional<MonthlyBudget> findByDate(Date date, Long userId);
    @Query("select case when (count(m)>0) then true else false end from MonthlyBudget m where m.user.id=:userId and m.startDate=:startDate and m.endDate=:endDate")
    Boolean existsByDate(Date startDate, Date endDate, Long userId);

}
