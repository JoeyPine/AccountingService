package com.joeypine.accounting.dao;

import com.joeypine.accounting.model.persistence.UserInfo;

public interface UserInfoDAO {

    UserInfo getUserInfoById(Long id);

    void createNewUser(String username, String password);
}
