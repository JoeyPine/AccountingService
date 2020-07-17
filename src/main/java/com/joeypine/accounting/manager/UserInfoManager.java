package com.joeypine.accounting.manager;


import com.joeypine.accounting.model.common.UserInfo;

public interface UserInfoManager {
    /**
     * Get user infomation by user id
     *
     * @param userId specific user id.
     * @return null;
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * Get user infomation by user username
     *
     * @param username specific user name.
     * @return null;
     */
    UserInfo getUserInfoByUserName(String username);


    /**
     * Login with username and password
     *
     * @param username username
     * @param password the related password
     */
    void login(String username, String password);

    /**
     * Register new user with username and password
     *
     * @param username username
     * @param password the related password
     * @return null;
     */
    UserInfo register(String username, String password);
}
