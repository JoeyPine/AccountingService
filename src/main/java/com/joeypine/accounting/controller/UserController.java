package com.joeypine.accounting.controller;

import com.joeypine.accounting.converter.c2s.UserInfoC2SConverter;
import com.joeypine.accounting.exception.InvalidParameterException;
import com.joeypine.accounting.manager.UserInfoManager;
import com.joeypine.accounting.model.service.UserInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// hostname:port/v1/users/...
@RestController
@RequestMapping("v1.0/users")
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
    public ResponseEntity<UserInfo> getUserInfoByUserId(@PathVariable("id") Long userId) {
        log.debug("Get user info by id {}", userId);
        if (userId == null || userId < 0L) {
            throw new InvalidParameterException(String.format("The user is %s is invalid", userId));
        }
        val userInfo = userInfoManager.getUserInfoByUserId(userId);
        val userInfoToReturn = userInfoC2SConverter.convert(userInfo);
        assert userInfoToReturn != null;
        return ResponseEntity.ok(userInfoToReturn);
    }

//    @PostMapping()
//    public ResponseEntity<UserInfo> register(@RequestParam("username") String username,
//                                             @RequestParam("password") String password) {
//        val userInfo = userInfoManager.register(username, password);
//        val userInfoService = userInfoC2SConverter.convert(userInfo);
//        assert userInfoService != null;
//        return ResponseEntity.ok(userInfoService);
//    }

    /**
     * Register with username and passwrod
     *
     * @param userinfo userInfo
     * @return The response for register
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserInfo> register2(@RequestBody com.joeypine.accounting.model.common.UserInfo userinfo) {
        val userInfoToReturn = userInfoC2SConverter.convert(
                userInfoManager.register(userinfo.getUsername(), userinfo.getPassword()));
        assert userInfoToReturn != null;
        return ResponseEntity.ok(userInfoToReturn);
    }

}
