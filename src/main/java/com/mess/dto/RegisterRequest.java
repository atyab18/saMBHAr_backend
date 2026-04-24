package com.mess.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String mobileNo;
    private String password;
    private String address;
    private String role; // USER or ADMIN
    private String messName;    // required if role=ADMIN
    private String messAddress; // optional, defaults to address
}
