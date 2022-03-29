package com.budgety.api.controller;

import com.budgety.api.payload.profile.ProfileDto;
import com.budgety.api.payload.user.UserDto;
import com.budgety.api.payload.user.UserUpdateRequest;
import com.budgety.api.service.ProfileService;
import com.budgety.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService, ProfileService profileService) {
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
