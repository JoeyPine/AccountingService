package com.joeypine.accounting.dao;

import com.joeypine.accounting.dao.mapper.UserInfoMapper;
import com.joeypine.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserInfoDAOTest {
    @Mock
    private UserInfoMapper userInfoMapper;

    private UserInfoDAO userInfoDAO;

    @BeforeEach
    public void setup() {
        userInfoDAO = new UserInfoDAOImpl(userInfoMapper);
    }

    @Test
    public void testGetUserInfoById() {
        //arrange
        val userId = 100L;
        val username = "test";
        val password = "test";
        val createTime = LocalDate.now();

        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();

        doReturn(userInfo).when(userInfoMapper).getUserInfoByUserId(userId);

        //act
        val result = userInfoDAO.getUserInfoById(userId);

        //assert
        assertEquals(userInfo, result);

        verify(userInfoMapper).getUserInfoByUserId(userId);
    }
}
