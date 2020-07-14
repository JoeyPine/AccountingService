package com.joeypine.accounting.converter;

import com.joeypine.accounting.converter.p2c.UserInfoP2CConverter;
import com.joeypine.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoP2CConverterTest {

    private UserInfoP2CConverter userInfoP2CConverter = new UserInfoP2CConverter();

    @Test
    void testDoForward(){
        //Arrange
        val userId = 100L;
        val username = "test";
        val password = "test";
        val createTime = LocalDate.now();

        val userInfoInPersistence = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();

        //Act
        val result = userInfoP2CConverter.convert(userInfoInPersistence);

        //Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    void testDoBackward(){
        //Arrange
        val userId = 100L;
        val username = "test";
        val password = "test";

        val userInfoInCommon = com.joeypine.accounting.model.common.UserInfo
                .builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();
        //Act
        val result = userInfoP2CConverter.reverse().convert(userInfoInCommon);

        //Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("createTime", null)
                .hasFieldOrPropertyWithValue("password", password);
    }
}
