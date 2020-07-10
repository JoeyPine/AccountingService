package com.joeypine.accounting.controller;

import com.joeypine.accounting.converter.c2s.UserInfoC2SConverter;
import com.joeypine.accounting.manager.UserInfoManager;
import com.joeypine.accounting.model.service.UserInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// hostname:port/v1/users/...
@RestController
@RequestMapping("v1/users")
@Slf4j
public class UserController {
    
    private final UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter;
    @Autowired
    public UserController(final UserInfoManager userInfoManager,
                          final UserInfoC2SConverter userInfoC2SConverter) {
        this.userInfoManager = userInfoManager;
        this.userInfoC2SConverter = userInfoC2SConverter;
    }

    @GetMapping("/{id}")
    public UserInfo getUserInfoByUserId(@PathVariable("id") Long userId) {
        log.debug("Get user info by id {}", userId);
        val userInfo = userInfoManager.getUserInfoByUserId(userId);

        return userInfoC2SConverter.convert(userInfo);
    }


}
