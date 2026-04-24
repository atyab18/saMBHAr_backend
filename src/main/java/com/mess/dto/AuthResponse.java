package com.mess.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private Long userId;
    private String name;
    private String mobileNo;
    private String address;
    private String role;
    private Long messId; // if role=ADMIN
}
