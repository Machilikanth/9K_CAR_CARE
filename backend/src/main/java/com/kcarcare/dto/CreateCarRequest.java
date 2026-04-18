package com.kcarcare.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateCarRequest {

    private String carNumber;
    private Long branchId;
    private LocalDateTime expectedDeliveryTime;
    private List<Long> serviceIds;
}