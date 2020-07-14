package com.joeypine.accounting.converter;

import com.joeypine.accounting.converter.c2s.UserInfoC2SConverter;
import com.joeypine.accounting.model.common.UserInfo;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoC2SConverterTest {

    private UserInfoC2SConverter userInfoC2SConverter = new UserInfoC2SConverter();

    @Test
    void testDoForward() {
        //Arrange
        val userId = 100L;
        val username = "test";
        val password = "test";

        val userInfoInCommon = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();

        //Act
        val result = userInfoC2SConverter.convert(userInfoInCommon);

        //Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    void testDoBackward() {
        //Arrange
        val userId = 100L;
        val username = "test";
        val password = "test";

        val userInfoInCommon = com.joeypine.accounting.model.service.UserInfo
                .builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();
        //Act
        val result = userInfoC2SConverter.reverse().convert(userInfoInCommon);

        //Assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
    }
}
