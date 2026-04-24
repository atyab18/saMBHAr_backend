package com.mess.dto;

import lombok.Data;

@Data
public class MenuRequest {
    private Long messId;
    private String comboName;
    private String items;
    private Double price;
}
