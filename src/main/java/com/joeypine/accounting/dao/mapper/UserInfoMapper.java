package com.joeypine.accounting.dao.mapper;

import com.joeypine.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoMapper {

    @Select("SELECT id , username , password , create_time , update_time FROM as_userinfo where id = #{id}")
    UserInfo getUserInfoByUserId(@Param("id") Long id);

    @Select("SELECT id , username , password , salt , create_time , update_time FROM as_userinfo where username = #{username }")
    UserInfo getUserInfoByUserName(@Param("username") String username);


    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT into as_userinfo(username , password , salt , create_time)" +
            " VALUES(#{username} , #{password} , #{salt} , #{createTime})")
    int createNewUser(UserInfo userInfo);
}
