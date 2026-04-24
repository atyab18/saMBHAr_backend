package com.mess.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String name;
    private String mobileNo;
    private String password;
    private String address;
}
