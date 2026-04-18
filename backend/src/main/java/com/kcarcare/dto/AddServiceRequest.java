package com.kcarcare.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddServiceRequest {

    private List<Long> serviceIds;
}