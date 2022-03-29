package com.budgety.api.payload.profile;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProfileDto {
    private Long id;

    private BigDecimal startingAmount;

    @NotEmpty
    @Length(min = 1, max = 3, message = "Currency length is invalid")
    private String currency;
}
