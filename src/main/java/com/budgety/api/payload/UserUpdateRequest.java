package com.budgety.api.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.math.BigDecimal;

@Data
public class UserUpdateRequest {
    @Email(message = "Email is not valid")
    private String email;
    private boolean confirmed;
    private BigDecimal accountBalance;
    @Length(max = 3, message = "Provide a valid currency")
    private String currency;
}
