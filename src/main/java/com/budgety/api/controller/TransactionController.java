package com.budgety.api.controller;

import com.budgety.api.payload.transaction.TransactionDeleteRequest;
import com.budgety.api.payload.transaction.TransactionDto;
import com.budgety.api.service.TransactionService;
import com.budgety.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/transactions")
public class TransactionController {

    private TransactionService transactionService;
    private UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<TransactionDto> createTransaction(
            @PathVariable Long userId,
            @Valid @RequestBody TransactionDto transactionDto) throws ParseException {
        TransactionDto expense = transactionService.createTransaction(userId, transactionDto);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable Long userId){
        List<TransactionDto> expenses = transactionService.findAllTransactionsByUserId(userId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<TransactionDeleteRequest> deleteTransaction(@PathVariable Long userId, @PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId, userId);
        TransactionDeleteRequest resp = new TransactionDeleteRequest();
        resp.setOk(true);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long userId, @PathVariable Long transactionId,@Valid @RequestBody TransactionDto transactionDto){
        TransactionDto updatedTransaction = transactionService.updateTransaction(userId, transactionId, transactionDto);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }
}
