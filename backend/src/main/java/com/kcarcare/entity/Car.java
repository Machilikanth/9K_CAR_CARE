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

    private String customerName;

    private String customerMobile;

    private String workerName;

    private String workerMobile;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    private LocalDateTime expectedDeliveryTime;

    private LocalDateTime createdAt = LocalDateTime.now();
}