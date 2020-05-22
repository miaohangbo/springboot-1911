package com.qf.dao;

import com.qf.domain.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 54110 on 2020/5/18.
 */
@Mapper
public interface UserLoginMapper {

    UserLogin findByUserName(@Param("username") String username);

    UserLogin findByPassword(String username);
}
