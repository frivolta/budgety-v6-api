package com.budgety.api.controller;

import com.budgety.api.payload.UserDto;
import com.budgety.api.payload.UserUpdateRequest;
import com.budgety.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserDto updatedUser = userService.updateUserByPrincipal(principal, userUpdateRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserId(Principal principal) {
        UserDto user = userService.getUser(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
