package com.joeypine.accounting.controller;

import com.joeypine.accounting.converter.c2s.UserInfoC2SConverter;
import com.joeypine.accounting.exception.GlobalExceptionHandler;
import com.joeypine.accounting.exception.ResourceNotFoundException;
import com.joeypine.accounting.manager.UserInfoManager;
import com.joeypine.accounting.model.common.UserInfo;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserInfoManager userInfoManager;
    @Mock
    private UserInfoC2SConverter userInfoC2SConverter;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    public void tearDown() {
        reset(userInfoManager);
        reset(userInfoC2SConverter);
    }

    @Test
    public void testGetUserInfoByUserId() throws Exception {
        //Arrange
        val userId = 100L;
        val username = "test";
        val password = "test";
        val createTime = LocalDate.now();

        val userInfoInCommon = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();

        val userInfo = com.joeypine.accounting.model.service.UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();

        doReturn(userInfoInCommon).when(userInfoManager).getUserInfoByUserId(anyLong());

        doReturn(userInfo).when(userInfoC2SConverter).convert(userInfoInCommon);


        //Act && Assert

        mockMvc.perform(get("/v1.0/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("{\"id\":100,\"username\":\"test\",\"password\":\"test\"}"));

        verify(userInfoManager).getUserInfoByUserId(anyLong());
        verify(userInfoC2SConverter).convert(userInfoInCommon);
    }

    @Test
    public void testGetUserInfoByUserIdWithInvalidUserId() throws Exception {
        //Arrange
        val userId = 100L;

        doThrow(new ResourceNotFoundException(String.format("The user is %s is invalid", userId)))
                .when(userInfoManager)
                .getUserInfoByUserId(anyLong());

        //Act && Assert
        mockMvc.perform(get("/v1.0/users/" + userId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("{\"code\":\"USER_INFO_NOT_FOUNDE\",\"errorType\":\"Client\",\"message\":\"The user is 100 is invalid\",\"statusCode\":404}"));

//        verify(userInfoManager, never()).getUserInfoByUserId(userId);
    }



}


