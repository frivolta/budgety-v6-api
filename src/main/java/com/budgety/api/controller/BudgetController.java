package com.budgety.api.controller;

import com.budgety.api.payload.budget.BudgetDto;
import com.budgety.api.payload.budget.CreateBudgetRequest;
import com.budgety.api.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/budgets")
public class BudgetController {
    private BudgetService budgetService;


    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping()
    public ResponseEntity<BudgetDto> createBudget(
            @PathVariable Long userId,
            @Valid @RequestBody CreateBudgetRequest createBudgetRequest
    ) {
        System.out.println("Here");
        BudgetDto newBudgetDto = budgetService.createBudget(
                createBudgetRequest.getBudget(),
                createBudgetRequest.getCategoryIds(),
                userId);
        return new ResponseEntity<>(newBudgetDto, HttpStatus.OK);
    }
}
