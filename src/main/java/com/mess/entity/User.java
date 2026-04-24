package com.mess.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "mobile_no", nullable = false, unique = true)
    private String mobileNo;

    @Column(nullable = false)
    private String password;

    private String address;

    @Column(nullable = false)
    private String role; // USER or ADMIN
}
