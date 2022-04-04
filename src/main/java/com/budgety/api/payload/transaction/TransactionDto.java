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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
public class TransactionDto {
    public final static DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Long id;
    @NotEmpty
    @Length(min = 3, max = 500, message = "Description cannot be less than 3 and more than 500")
    private String transactionDescription;
    @DecimalMax("999999.99")
    @DecimalMin("0.01")
    private BigDecimal amount;
    @JsonFormat(pattern="dd-MM-yyyy")
    private String date;
    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
    @Enumerated(EnumType.STRING)
    private CategoryType type;


    public void setDate(LocalDate date){
        this.date = dateToString(date);
    }


    public LocalDate getDate() {
        return stringToDate(this.date);
    }

    // Convert methods
    private LocalDate stringToDate(String inputString) {
        return LocalDate.parse(inputString, formatters);
    }

    private String dateToString(LocalDate inputDate){
        return inputDate.format(formatters);
    }
}
