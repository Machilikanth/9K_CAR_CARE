package com.kcarcare.dto;

import lombok.Data;

@Data
public class UpdateStatusRequest {

    private String status;
    private String remarks;
}