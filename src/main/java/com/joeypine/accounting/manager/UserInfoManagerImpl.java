package com.joeypine.accounting.manager;

import com.joeypine.accounting.converter.p2c.UserInfoP2CConverter;
import com.joeypine.accounting.dao.UserInfoDAO;
import com.joeypine.accounting.exception.InvalidParameterException;
import com.joeypine.accounting.exception.ResourceNotFoundException;
import com.joeypine.accounting.model.common.UserInfo;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * @param username The username
 * @param password The user password
 * @return userInfoCommon
 */

@Component
public class UserInfoManagerImpl implements UserInfoManager {

    public static final int HASH_ITERATIONS = 1000;
    private final UserInfoDAO userInfoDao;
    private final UserInfoP2CConverter userInfoP2CConverter;

    //构造器注入
    @Autowired
    public UserInfoManagerImpl(final UserInfoDAO userInfoDao,
                               final UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoDao = userInfoDao;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }


    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        return Optional.ofNullable(userInfoDao.getUserInfoById(userId))
                .map(userInfoP2CConverter::convert)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user %s not found", userId)));
    }

    @Override
    public UserInfo getUserInfoByUserName(String username) {
        return Optional.ofNullable(userInfoDao.getUserInfoByUserName(username))
                .map(userInfoP2CConverter::convert)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user name %s not found", username)));
    }

    @Override
    public void login(String username, String password) {
        //Get shiro Subject
        val subject = SecurityUtils.getSubject();
        //Construct token
        val usernamePasswordToken = new UsernamePasswordToken(username, password);

        //login
        subject.login(usernamePasswordToken);
    }

    @Override
    public UserInfo register(String username, String password) {
        val userInfo = userInfoDao.getUserInfoByUserName(username);
        if (userInfo != null) {
            throw new InvalidParameterException(String.format("The user %s was registed.", username));
        }

        //Set random salt
        String salt = UUID.randomUUID().toString();
        String encryptedPassword = new Sha256Hash(password, salt, HASH_ITERATIONS).toBase64();

        val newUserInfo = com.joeypine.accounting.model.persistence.UserInfo.builder()
                .username(username)
                .password(encryptedPassword)
                .salt(salt)
                .createTime(LocalDate.now())
                .build();

        userInfoDao.createNewUser(newUserInfo);
        return userInfoP2CConverter.convert(newUserInfo);


    }
}
