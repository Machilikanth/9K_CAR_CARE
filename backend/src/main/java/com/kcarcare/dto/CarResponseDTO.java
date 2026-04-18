package com.kcarcare.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarResponseDTO {

    private String carNumber;
    private String status;
    private LocalDateTime expectedDeliveryTime;
    private List<ServiceDTO> services;
    private BigDecimal totalPrice;
}