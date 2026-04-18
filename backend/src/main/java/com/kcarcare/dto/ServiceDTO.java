package com.kcarcare.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceDTO {

    private String name;
    private BigDecimal price;
}