package com.budgety.api.payload.monthlyBudget;

import com.budgety.api.entity.EnrichedCategory;
import com.budgety.api.entity.Transaction;
import com.budgety.api.payload.enrichedCategory.EnrichedCategoryDto;
import com.budgety.api.payload.transaction.TransactionDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class MonthlyBudgetDto {
    public final static DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Long id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private String startDate;
    @JsonFormat(pattern="dd-MM-yyyy")
    private String endDate;

    private Set<EnrichedCategoryDto> enrichedCategories;
    private Set<TransactionDto> transactions;

    public void setStartDate(LocalDate date){
        this.startDate = dateToString(date);
    }

    public void setEndDate(LocalDate date){
        this.endDate = dateToString(date);
    }


    public LocalDate getStartDate() throws ParseException {
        return stringToDate(this.startDate);
    }

    public LocalDate getEndDate() throws ParseException {
        return stringToDate(this.endDate);
    }

    // Convert methods
    private LocalDate stringToDate(String inputString) {
        return LocalDate.parse(inputString, formatters);
    }

    private String dateToString(LocalDate inputDate){
        return inputDate.format(formatters);
    }

}
