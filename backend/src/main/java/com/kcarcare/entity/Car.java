package com.kcarcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "car")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carNumber;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    private LocalDateTime expectedDeliveryTime;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private LocalDateTime createdAt = LocalDateTime.now();
}