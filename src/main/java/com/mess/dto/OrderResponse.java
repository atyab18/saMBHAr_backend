package com.mess.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String userName;
    private String userMobile;
    private String userAddress;
    private Long messId;
    private String messName;
    private Long comboId;
    private String comboName;
    private Integer quantity;
    private Double totalPrice;
    private String status;
    private String otp;
    private LocalDateTime createdAt;
}
