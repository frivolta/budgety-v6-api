package com.budgety.api.service;

import com.budgety.api.entity.User;
import com.budgety.api.payload.UserDto;

public interface UserService {
    UserDto getUser(String sub);
    boolean userExists(String sub);
    UserDto createUser(UserDto userDto);
}
