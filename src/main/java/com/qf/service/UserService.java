package com.qf.service;

import com.qf.domain.User;

import java.util.List;

/**
 * Created by 54110 on 2020/5/18.
 */
public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    void sendMail(String p0);

    void registry(String username, String password, String email,String code);
}
