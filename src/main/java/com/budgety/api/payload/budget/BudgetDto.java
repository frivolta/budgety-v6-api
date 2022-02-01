package com.budgety.api.payload.budget;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.Expense;
import com.budgety.api.payload.category.CategoryDto;
import com.budgety.api.payload.expense.ExpenseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class BudgetDto {
    private Long id;
    @NotEmpty
    @Length(min = 3, max = 50, message = "Budget name should be between 3 and 50 characters")
    private String name;
    @NotEmpty
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum amount should be greater than zero")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal maxAmount;
    private BigDecimal leftAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fromDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date toDate;
    private Set<ExpenseDto> expenses;
    private Set<CategoryDto> categories;
}
