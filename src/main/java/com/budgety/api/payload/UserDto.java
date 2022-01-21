package com.budgety.api.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
    private Long id;
    @NotEmpty
    private String sub;
    @NotEmpty
    @Email(message = "Email is not valid")
    private String email;
    private boolean confirmed;
}
