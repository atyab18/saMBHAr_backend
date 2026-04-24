package com.mess.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mess")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Mess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
}
