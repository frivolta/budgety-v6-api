package com.budgety.api.controller;
import com.budgety.api.payload.user.UserDto;
import com.budgety.api.service.UserService;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.security.Principal;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserController controller;

    Principal mockPrincipal = Mockito.mock(Principal.class);
    private final String mockedSub = "sub@user";
    UserDto mockedUser = new UserDto();

    UserControllerTest(){
        mockedUser.setEmail("email@email.com");
        mockedUser.setSub(mockedSub);
    }

    @Test
    public void contextLoads() throws Exception{
        assertThat(controller).isNotNull();
    }

    @Test
    void updateUserByPrincipal() {
    }

    @Test
    @WithMockUser(username = mockedSub)
    void getUserId_shouldReturnUserDto() throws Exception{
        when(mockPrincipal.getName()).thenReturn(mockedSub);
       when(userService.getUser(mockedSub)).thenReturn(mockedUser);
       MvcResult result = mockMvc.perform(get("/api/users/me")
               .principal(mockPrincipal))
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();
       String content = result.getResponse().getContentAsString();
       assertThat(content).contains(mockedSub);
    }

    @Test
    void getUserId_shouldReturn401WithoutPrincipal() throws Exception{
        when(mockPrincipal.getName()).thenReturn(mockedSub);
        when(userService.getUser(mockedSub)).thenReturn(mockedUser);
        MvcResult result = mockMvc.perform(get("/api/users/me")
                        .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        result.getResponse();
    }

    @Test
    @WithMockUser(username = mockedSub)
    void getUserId_shouldReturnErrorIfNoUserFound() throws Exception{
        when(mockPrincipal.getName()).thenReturn(mockedSub);
        when(userService.getUser(mockedSub)).
                thenThrow(new IllegalArgumentException("User not found with sub "+ mockedSub));
        MvcResult result = mockMvc.perform(get("/api/users/me")
                        .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("User not found with sub sub@user");
    }
}