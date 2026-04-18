package com.kcarcare.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "car_service")
@Data
public class CarService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @ManyToOne
    private ServiceEntity service;
}