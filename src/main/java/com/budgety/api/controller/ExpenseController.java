package com.budgety.api.controller;

import com.budgety.api.entity.User;
import com.budgety.api.exceptions.NotAllowedException;
import com.budgety.api.payload.ExpenseDeleteRequest;
import com.budgety.api.payload.ExpenseDto;
import com.budgety.api.service.ExpenseService;
import com.budgety.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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
            Principal principal,
            @PathVariable Long userId,
            @Valid @RequestBody ExpenseDto expenseDto) throws NotAllowedException {
        User principalUser = userService.getUserEntity(principal.getName());
        if (userId != principalUser.getId()) {
            throw new NotAllowedException("Not allowed", principal.getName());
        }
        ExpenseDto expense = expenseService.createExpense(principal, expenseDto);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<ExpenseDeleteRequest> deleteExpense(Principal principal, @PathVariable Long userId, @PathVariable Long expenseId) throws NotAllowedException {
        User principalUser = userService.getUserEntity(principal.getName());
        if (userId != principalUser.getId()) {
            throw new NotAllowedException("Not allowed", principal.getName());
        }
        expenseService.deleteExpense(expenseId, userId);
        ExpenseDeleteRequest resp = new ExpenseDeleteRequest();
        resp.setOk(true);
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }
}
