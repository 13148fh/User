package com.example.demo.model.domain.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -2545474273378183048L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
