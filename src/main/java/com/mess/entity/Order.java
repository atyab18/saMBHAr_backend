package com.mess.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "mess_id", nullable = false)
    private Long messId;

    @Column(name = "combo_id", nullable = false)
    private Long comboId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String status; // ACTIVE, COMPLETED

    @Column(length = 4)
    private String otp;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
