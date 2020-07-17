package com.joeypine.accounting.dao;

import com.joeypine.accounting.model.persistence.UserInfo;

public interface UserInfoDAO {

    UserInfo getUserInfoById(Long id);
    UserInfo getUserInfoByUserName(String username);

    void createNewUser(UserInfo userInfo);


}
