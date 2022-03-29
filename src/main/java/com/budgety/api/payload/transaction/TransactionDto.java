package com.budgety.api.payload.transaction;

import com.budgety.api.entity.CategoryType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TransactionDto {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Long id;
    @NotEmpty
    @Length(min = 3, max = 500, message = "Description cannot be less than 3 and more than 500")
    private String transactionDescription;
    @DecimalMax("999999.99")
    @DecimalMin("0.01")
    private BigDecimal amount;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    @NotEmpty
    private String categoryName;
    @Enumerated(EnumType.STRING)
    private CategoryType type;
}
