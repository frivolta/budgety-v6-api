package com.budgety.api.controller;

import com.budgety.api.payload.budget.BudgetDto;
import com.budgety.api.payload.budget.CreateBudgetRequest;
import com.budgety.api.payload.budget.UpdateBudget;
import com.budgety.api.payload.common.DefaultResponse;
import com.budgety.api.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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
        BudgetDto newBudgetDto = budgetService.createBudget(
                createBudgetRequest.getBudget(),
                createBudgetRequest.getCategoryIds(),
                userId);
        return new ResponseEntity<>(newBudgetDto, HttpStatus.OK);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> updateBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @Valid @RequestBody UpdateBudget updateBudget
            ){
        BudgetDto newBudgetDto = budgetService.updateBudget(
                budgetId,
                updateBudget.getBudget(),
                updateBudget.getCategoryIds(),
                userId
        );
        return new ResponseEntity<>(newBudgetDto, HttpStatus.OK);
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> getBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId
    ){
        BudgetDto newBudgetDto = budgetService.getBudget(
                budgetId,
                userId
        );
        return new ResponseEntity<>(newBudgetDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<BudgetDto>> getAllBudgets(
            @PathVariable Long userId
    ){
        Set<BudgetDto> budgets = budgetService.getAllBudgets(
            userId
        );
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<DefaultResponse> deleteBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId
    ){
        budgetService.deleteBudget(
                budgetId,
                userId
        );
        DefaultResponse ok = new DefaultResponse();
        ok.setOk(true);
        return new ResponseEntity<>(ok, HttpStatus.OK);
    }
}
