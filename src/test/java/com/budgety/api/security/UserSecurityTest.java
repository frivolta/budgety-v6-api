package com.budgety.api.security;

import com.budgety.api.entity.User;
import com.budgety.api.repository.UserRepository;
import com.budgety.api.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserSecurityTest {
    @Spy
    @InjectMocks
    private UserSecurity userSecurity;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    private Long defaultUserId = 1L ;
    private String defaultSub = "defaultSub";

    @Test
    @DisplayName("Should return true if userId equals Authentication")
    void hasUserId_shouldReturnTrue(){
        User defaultUser = new User();
        defaultUser.setSub(defaultSub);
        defaultUser.setId(defaultUserId);
        Mockito.when(authentication.getName()).thenReturn(defaultSub);
        Mockito.when(userService.getUserEntity(defaultSub)).thenReturn(defaultUser);
        boolean result = userSecurity.hasUserId(authentication, defaultUserId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false if userId differs from Authentication")
    void hasUserId_shouldReturnFalse(){
        User defaultUser = new User();
        defaultUser.setSub(defaultSub);
        defaultUser.setId(4);
        Mockito.when(authentication.getName()).thenReturn(defaultSub);
        Mockito.when(userService.getUserEntity(defaultSub)).thenReturn(defaultUser);
        boolean result = userSecurity.hasUserId(authentication, defaultUserId);
        assertFalse(result);
    }

}