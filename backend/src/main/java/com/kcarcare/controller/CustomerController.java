package com.kcarcare.controller;

import com.kcarcare.dto.CustomerStatusResponseDTO;
import com.kcarcare.service.CarAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/cars")
public class CustomerController {

    @Autowired
    private CarAdminService carAdminService;

    @GetMapping("/{carNumber}/status")
    public ResponseEntity<CustomerStatusResponseDTO> getStatus(@PathVariable String carNumber) {
        CustomerStatusResponseDTO status = carAdminService.getCustomerStatus(carNumber);
        return ResponseEntity.ok(status);
    }
}