package com.qf.service.Impl;

import com.qf.dao.UserLoginMapper;
import com.qf.dao.UserMapper;
import com.qf.dao.responsetory.UserLoginRepository;
import com.qf.dao.responsetory.UserRespostory;
import com.qf.domain.User;
import com.qf.domain.UserLogin;
import com.qf.service.UserService;
import com.qf.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 54110 on 2020/5/18.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRespostory userResponsitory;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtils redisUtils;

    @Value("${lance.send}")
    private String mail_send;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserLoginMapper userLoginMapper;

    @Autowired
    UserLoginRepository userLoginRepository;
    @Override
    public List<User> findAll() {

        List<User> all = userResponsitory.findAll();

        return all;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 发送邮件，发送成功后，需要将验证码存储到我们的redis中
     * @param mail
     */
    @Override
    public void sendMail(String mail) {

        String l = "1234";
        //发送邮件
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(mail_send);
            simpleMailMessage.setTo(mail);
            simpleMailMessage.setSubject("腾吧吧验证码");
            simpleMailMessage.setText(String.valueOf(l));
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //存储验证码
        boolean set = redisUtils.set(mail, l);
        redisUtils.expire(mail,6000);
        System.out.println(set);
    }

    @Override
    public void registry(String username, String password, String email,String code) {
        //判断用户输入的验证码是否正确
        String o = (String)redisUtils.get(email);
        if (!o.equals(code)){
            System.out.println("验证码错误");
        }
        //通过用户名去数据库查询是否唯一
       UserLogin user = userLoginMapper.findByUserName(username);
        if (user!=null){
            System.out.println("用户名已被注册");
        }
        user = new UserLogin();
        user.setPassword(password);
        user.setUserName(username);
        UserLogin save = userLoginRepository.save(user);
        if (save!=null){
            System.out.println("用户注册成功");
        }
    }
}
