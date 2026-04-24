package com.mess.dto;

import lombok.Data;

@Data
public class OtpRequest {
    private Long orderId;
    private String otp;
}
