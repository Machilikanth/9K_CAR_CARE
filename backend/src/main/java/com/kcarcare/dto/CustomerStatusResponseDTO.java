package com.kcarcare.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerStatusResponseDTO {

    private String carNumber;
    private String customerName;
    private List<String> serviceTypes;
    private String status;
    private String workerName;
    private LocalDateTime estimatedDeliveryTime;
    private String message;
}