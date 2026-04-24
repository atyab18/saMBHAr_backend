package com.mess.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Long messId;
    private Long comboId;
    private Integer quantity;
}
