package com.example.demo.service;

import com.example.demo.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author chenlian
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-01-13 15:15:09
*/
public interface UserService extends IService<User> {
//    String USER_LOGIN_STATE = "userLoginState";
    long UserRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafetyUser(User user);
    int userLogout(HttpServletRequest request);
}
