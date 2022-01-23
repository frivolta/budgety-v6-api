package com.budgety.api.controller;

import com.budgety.api.payload.UserDto;
import com.budgety.api.payload.UserUpdateRequest;
import com.budgety.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // A user wants to update his profile using his jwt info
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUserByPrincipal(
            Principal principal,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest){
            UserDto updatedUser = userService.updateUserByPrincipal(principal, userUpdateRequest);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
