package com.qf.dao;

import com.qf.domain.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 54110 on 2020/5/18.
 */
@Mapper
public interface SysPermissionMapper {

    List<SysPermission> findPermissionByUserName(@Param("username")String username);
}
