package com.joeypine.accounting.dao.mapper;

import com.joeypine.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("SELECT id,username,password,create_time,update_time FROM as_userinfo where id = #{id}")
     UserInfo getUserInfoByUserId(@Param("id") Long id);
}
