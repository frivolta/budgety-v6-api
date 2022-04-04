package com.budgety.api.controller;

import com.budgety.api.payload.common.DefaultResponse;
import com.budgety.api.payload.monthlyBudget.MonthlyBudgetDto;
import com.budgety.api.service.MonthlyBudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<DefaultResponse> deleteMonthlyBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId
    ){
        boolean ok = monthlyBudgetService.deleteMonthlyBudget(userId, budgetId);
        DefaultResponse res = new DefaultResponse();
        res.setOk(ok);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PutMapping("/{budgetId}")
    public ResponseEntity<MonthlyBudgetDto> updateMonthlyBudget(
            @PathVariable Long userId,
            @PathVariable Long budgetId,
            @Valid @RequestBody MonthlyBudgetDto monthlyBudgetDto
    ){
        MonthlyBudgetDto dto = monthlyBudgetService.updateMonthlyBudget(userId, budgetId, monthlyBudgetDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/id/{budgetId}")
    public ResponseEntity<MonthlyBudgetDto> getMonthlyBudgetById(
            @PathVariable Long userId,
            @PathVariable Long budgetId
    ){
        MonthlyBudgetDto dto = monthlyBudgetService.getMonthlyBudget(userId, budgetId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/date/{dateAsString}")
    public ResponseEntity<MonthlyBudgetDto> getMonthlyBudgetByDate(
            @PathVariable Long userId,
            @PathVariable String dateAsString
            ) throws ParseException {
        LocalDate parsedDate = LocalDate.parse(dateAsString, MonthlyBudgetDto.formatters);
        MonthlyBudgetDto dto = monthlyBudgetService.getMonthlyBudgetByDate(userId, parsedDate);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
