package com.kcarcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "car_history")
@Data
public class CarHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    private LocalDateTime updatedAt = LocalDateTime.now();

    private String remarks;
}