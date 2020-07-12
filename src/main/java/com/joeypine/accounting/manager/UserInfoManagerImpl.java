package com.joeypine.accounting.manager;

import com.joeypine.accounting.converter.p2c.UserInfoP2CConverter;
import com.joeypine.accounting.dao.UserInfoDAO;
import com.joeypine.accounting.exception.ResourceNotFoundException;
import com.joeypine.accounting.model.common.UserInfo;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoManagerImpl implements UserInfoManager {

    private final UserInfoDAO userInfoDAO;
    private final UserInfoP2CConverter userInfoP2CConverter;

    //构造器注入
    public UserInfoManagerImpl(final UserInfoDAO userInfoDAO,
                               final UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoDAO = userInfoDAO;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }


    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        val userInfo = Optional.ofNullable(userInfoDAO.getUserInfoById(userId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user %s not found", userId)));
        return userInfoP2CConverter.convert(userInfo);
    }
}