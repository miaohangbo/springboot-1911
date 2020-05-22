package com.qf.controller;

import com.qf.domain.User;
import com.qf.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by 54110 on 2020/5/18.
 */
@Controller
public class UserController {


    @Autowired
    UserService userService;

    @RequiresPermissions(value = {"user_edit"})
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    //@ResponseBody
    public ModelAndView findAll(){
        List<User> all = userService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("UserList");
        modelAndView.addObject(all);
        return modelAndView;
    }

    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable("id")Integer id){
      return   userService.findById(id);
    }

    @RequestMapping(value = "/toRegistry",method = RequestMethod.GET)
    public String toRegistry(){
        return "registry";
    }

    //注册接口
    @RequestMapping(value = "/registry",method = RequestMethod.POST)
    public ModelAndView registry(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("mail")String email,@RequestParam("code")String code){

        userService.registry(username,password,email,code);
            ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Login");

        return modelAndView;
    }

    //发送邮件
    @RequestMapping(value = "/sendMail",method = RequestMethod.POST)
    @ResponseBody
    public void sendEmail(@RequestParam("mail")String mail){
        userService.sendMail(mail);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("username")String username ,@RequestParam("password")String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);
            return "redirect:/findAll";
        }catch (Exception e){
            return "login";
        }
    }
    @RequestMapping("/unauth")
    public String unauth(){
        return "unauth";
    }
}
