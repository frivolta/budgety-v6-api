package com.budgety.api.payload.expense;

import com.budgety.api.entity.Category;
import com.budgety.api.entity.CategoryType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class ExpenseDto {
    private Long id;
    @NotEmpty
    @Length(min = 3, max = 500, message = "Description cannot be less than 3 and more than 500")
    private String expenseDescription;
    @DecimalMax("999999.99")
    @DecimalMin("0.01")
    private BigDecimal amount;
    @NotEmpty
    private String categoryName;
    @Enumerated(EnumType.STRING)
    private CategoryType type;
}
