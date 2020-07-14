package com.joeypine.accounting.manager;


import com.joeypine.accounting.model.common.UserInfo;

public interface UserInfoManager {
    /**
     * Get user infomation by user id
     * @param userId specific user id.
     * @return null;
     */
    UserInfo getUserInfoByUserId(Long userId);
}
