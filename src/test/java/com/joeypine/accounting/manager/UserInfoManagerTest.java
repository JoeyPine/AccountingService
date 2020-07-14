package com.joeypine.accounting.manager;

import com.joeypine.accounting.converter.p2c.UserInfoP2CConverter;
import com.joeypine.accounting.dao.UserInfoDAO;
import com.joeypine.accounting.exception.ResourceNotFoundException;
import com.joeypine.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class UserInfoManagerTest {

    private UserInfoManagerImpl userInfoManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userInfoManager = new UserInfoManagerImpl(userInfoDAO, new UserInfoP2CConverter());
    }

    @Mock
    private UserInfoDAO userInfoDAO;

    @Test
    public void testGetUserInfoByUserId() {
        //arrange
        val userId = 1L;
        val username = "test";
        val password = "test";
        val createTime = LocalDate.now();

        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();

        doReturn(userInfo).when(userInfoDAO).getUserInfoById(userId);
        //act
        val result = userInfoManager.getUserInfoByUserId(userId);

        //assert
        //AssertJ
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);

        verify(userInfoDAO).getUserInfoById(eq(userId));
    }

    @Test
    public void testGetUserInfoByUserIdWithInvalidUserId() {
        //arrange
        val userId = -1L;

        doReturn(null).when(userInfoDAO).getUserInfoById(userId);
        //act & assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoManager.getUserInfoByUserId(userId));
        verify(userInfoDAO).getUserInfoById(eq(userId));
    }

}
