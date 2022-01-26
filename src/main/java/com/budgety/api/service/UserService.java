package com.budgety.api.service;

import com.budgety.api.entity.User;
import com.budgety.api.payload.UserDto;
import com.budgety.api.payload.UserUpdateRequest;

import java.security.Principal;

public interface UserService {
    UserDto getUser(String sub);
    User getUserEntity(String sub);
    boolean userExists(String sub);
    UserDto createUser(UserDto userDto);
    UserDto updateUserByPrincipal(Principal principal, UserUpdateRequest userUpdateRequest);
}
