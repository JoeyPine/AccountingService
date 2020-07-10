package com.joeypine.accounting.converter.c2s;

import com.google.common.base.Converter;
import com.joeypine.accounting.model.common.UserInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserInfoC2SConverter extends Converter<UserInfo, com.joeypine.accounting.model.service.UserInfo> {

    @Override
    protected com.joeypine.accounting.model.service.UserInfo doForward(UserInfo userInfo) {
        return com.joeypine.accounting.model.service.UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }

    @Override
    protected UserInfo doBackward(com.joeypine.accounting.model.service.UserInfo userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
    }
