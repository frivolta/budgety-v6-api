package com.budgety.api.controller;

import com.budgety.api.payload.expense.ExpenseDeleteRequest;
import com.budgety.api.payload.expense.ExpenseDto;
import com.budgety.api.service.ExpenseService;
import com.budgety.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/expenses")
public class ExpenseController {

    private ExpenseService expenseService;
    private UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<ExpenseDto> createExpense(
            @PathVariable Long userId,
            @Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto expense = expenseService.createExpense(userId, expenseDto);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathVariable Long userId){
        List<ExpenseDto> expenses = expenseService.findAllExpensesByUser(userId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<ExpenseDeleteRequest> deleteExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId, userId);
        ExpenseDeleteRequest resp = new ExpenseDeleteRequest();
        resp.setOk(true);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long userId, @PathVariable Long expenseId, @RequestBody ExpenseDto expenseDto){
        ExpenseDto updatedExpense = expenseService.updateExpense(userId, expenseId, expenseDto);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }
}
