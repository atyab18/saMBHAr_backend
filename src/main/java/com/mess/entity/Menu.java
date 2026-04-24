package com.mess.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mess_id", nullable = false)
    private Long messId;

    @Column(name = "combo_name", nullable = false)
    private String comboName;

    @Column(length = 500)
    private String items;

    @Column(nullable = false)
    private Double price;
}
