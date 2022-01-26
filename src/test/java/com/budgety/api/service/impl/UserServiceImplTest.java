package com.budgety.api.service.impl;

import com.budgety.api.entity.User;
import com.budgety.api.payload.UserDto;
import com.budgety.api.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.*;


import java.security.Principal;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Principal principal;

    @Mock
    private UserDto userDtoStub;

    private String defaultSub = "123456";

    @Test
    void getUser_shouldReturnADtoWhenUserFound() {
        Mockito.when(userRepository.findBySub(defaultSub)).thenReturn(java.util.Optional.of(new User()));
        Mockito.when(userService.getUser(defaultSub)).thenReturn(userDtoStub);
        // when
        UserDto userFound = userService.getUser(defaultSub);
        // then
        assertEquals(userFound, userDtoStub);
    }

    @Test
    void getUser_shouldReturnUserNotFoundIfNotInDb(){
        Exception thrown =    assertThrows(IllegalArgumentException.class, ()->
            userService.getUser(defaultSub)
        ,"Expected getUser() to throw but it didn't");
        // when
        assertTrue(thrown.getMessage().equals("User not found with sub 123456"));
    }

    @Test
    void userExits_shouldReturnTrueIfExists(){
        Mockito.when(userService.userExists(defaultSub)).thenReturn(true);
        boolean userFound = userService.userExists(defaultSub);
        assertTrue(userFound);
    }

    @Test
    void userExists_shouldReturnFalseIfNotInDb(){
        Mockito.when(userService.userExists(defaultSub)).thenReturn(false);
        boolean userFound = userService.userExists(defaultSub);
        assertFalse(userFound);
    }

    @Test
    void createUser_shouldReturnAUserDto(){
        Mockito.when(userService.createUser(userDtoStub)).thenReturn(userDtoStub);
        UserDto user = userService.createUser(userDtoStub);
        assertEquals(user, userDtoStub);
    }

    @Test
    void createUser_shouldReturnNullIfNotOk(){
        UserDto user = userService.createUser(userDtoStub);
        assertEquals(user, null);
    }

}