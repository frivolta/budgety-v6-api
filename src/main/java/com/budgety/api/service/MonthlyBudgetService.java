package com.budgety.api.service;

import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface MonthlyBudgetService {
    MonthlyBudgetDto createMonthlyBudget(Long userId, MonthlyBudgetDto monthlyBudgetDto) throws ParseException;
    MonthlyBudgetDto getMonthlyBudget(Long userId, Long budgetId);
    Set<MonthlyBudgetDto> getMonthlyBudgets(Long userId);
    MonthlyBudgetDto getMonthlyBudgetByDate(Long userId, Date date);
    boolean deleteMonthlyBudget(Long userId, Long budgetId);
    MonthlyBudgetDto updateMonthlyBudget(Long userId, Long budgetId, MonthlyBudgetDto monthlyBudgetDto);
}
