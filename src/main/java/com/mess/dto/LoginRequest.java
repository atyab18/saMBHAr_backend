package com.mess.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String mobileNo;
    private String password;
}
