package com.budgety.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = "test_user")
@ActiveProfiles("test")
@Sql({"/sql/profile/insert_data1.sql", "/sql/user/insert_data1.sql"})
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void getProfile_shouldGetProfile() throws Exception {
        mockMvc.perform(get("/api/users/1/profile")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("$"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.startingAmount").value(9999.45))
                .andReturn();
    }

    @Test
    void getProfile_shouldNotGetProfileIfPathNotMatchUserId()throws Exception{
        mockMvc.perform(get("/api/users/2/profile")).andDo(print())
                .andExpect(status().is4xxClientError());
    }
}