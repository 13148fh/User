package com.example.demo.service;
import java.util.Date;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService uesrService;
    @Test
    public void testAddUser(){
        User user =new User();
        user.setUserAccount("123");
        user.setUsername("dogYupi");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("1234");
        user.setEmail("456");
        boolean result =uesrService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);


    }

    @Test
    void userRegister() {
        String userAccount = "chen11";
        String password = "12341234";
        String check = "12341234";
        long r = uesrService.UserRegister(userAccount, password, check);
        Assertions.assertTrue(r>0);

    }


    @Test
    void userLogin() {
        String userAccount = "chen11";
        String password = "12341234";

    }
}