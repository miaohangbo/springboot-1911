package com.qf.config;

import com.qf.dao.SysPermissionMapper;
import com.qf.dao.UserLoginMapper;
import com.qf.domain.SysPermission;
import com.qf.domain.UserLogin;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 54110 on 2020/5/18.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    //用户的权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取到主体中的用户名
        String username =(String) principalCollection.getPrimaryPrincipal();
        List<SysPermission> permissionByUserName = sysPermissionMapper.findPermissionByUserName(username);
       Collection<String> objects = new HashSet<>();
        for (SysPermission per:permissionByUserName){
            objects.add(per.getPerName());
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(objects);
        return simpleAuthorizationInfo;
    }

    //用户的登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        UserLogin user = userLoginMapper.findByPassword(username);

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}
