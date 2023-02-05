package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.BaseResponse;
import com.example.demo.common.ErrorCode;
import com.example.demo.common.ResultUtils;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.domain.User;
import com.example.demo.model.domain.request.UserLoginRequest;
import com.example.demo.model.domain.request.UserRegisterRequest;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.contant.UserConstant.ADMIN_ROLE;
import static com.example.demo.contant.UserConstant.USER_LOGIN_STATE;

@RestController  //返回json

@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser ==null){
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user =userService.getById(userId);
        return ResultUtils.success(userService.getSafetyUser(user));
    }
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
    if(userRegisterRequest ==null){
        throw new BusinessException(ErrorCode.PARMS_ERROR);
    }
    String userAccount = userRegisterRequest.getUserAccount();
    String userPassword = userRegisterRequest.getUserPassword();
    String checkPassword = userRegisterRequest.getCheckPassword();

    if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
        throw new BusinessException(ErrorCode.PARMS_ERROR);
    }
    long result = userService.UserRegister(userAccount, userPassword, checkPassword);
    return ResultUtils.success(result);
    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogin(HttpServletRequest request){
        if(request ==null){
            return null;
        }
        int result= userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogout(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest ==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        User user=userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
        //仅管理员可以查询
        if(!isAdmin(request)){
            //return new ArrayList<>();
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("userName",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user ->userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody long id,HttpServletRequest request){

        if(!isAdmin(request)){
            return null;
        }

        if (id<=0){
            return null;
        }
        boolean b =userService.removeById(id);
        return ResultUtils.success(b);
    }
    //是否为管理员
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)  userObj;
        if (user == null ||user.getUserRole()!=ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
