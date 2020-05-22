package com.qf.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by 54110 on 2020/5/18.
 */
@ControllerAdvice
public class ExcepitonController {

    @ExceptionHandler(AuthorizationException.class)
    public String AuthorzationHandle(){
        return "unauth";
    }


}
