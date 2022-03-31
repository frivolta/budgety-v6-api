package com.budgety.api.controller;

import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;
import com.budgety.api.service.MonthlyBudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("/api/users/{userId}/monthly-budget")
public class MonthlyBudgetController {
    private MonthlyBudgetService monthlyBudgetService;

    public MonthlyBudgetController(MonthlyBudgetService monthlyBudgetService) {
        this.monthlyBudgetService = monthlyBudgetService;
    }

    @PostMapping()
    public ResponseEntity<MonthlyBudgetDto> createMonthlyBudget(
            @PathVariable Long userId,
            @Valid @RequestBody MonthlyBudgetDto monthlyBudgetDto
    ) throws ParseException {
        MonthlyBudgetDto newMonthlyBudgetDto = monthlyBudgetService.createMonthlyBudget(userId, monthlyBudgetDto);
        return new ResponseEntity<>(newMonthlyBudgetDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Set<MonthlyBudgetDto>> getMonthlyBudgets(
            @PathVariable Long userId
    ){
        Set<MonthlyBudgetDto> monthlyBudgetDtos = monthlyBudgetService.getMonthlyBudgets(userId);
        return new ResponseEntity<>(monthlyBudgetDtos, HttpStatus.OK);
    }
    // Delete budget
    // Update budget
    // Get budget by id
    // Get budget by date
    // Get Budgets
}
