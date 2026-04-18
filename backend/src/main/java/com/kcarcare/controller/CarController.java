package com.kcarcare.controller;

import com.kcarcare.dto.CarResponseDTO;
import com.kcarcare.service.CarQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarQueryService carQueryService;

    @GetMapping("/{carNumber}")
    public ResponseEntity<CarResponseDTO> getCarDetails(
            @PathVariable String carNumber) {

        CarResponseDTO response = carQueryService.getCarDetails(carNumber);

        return ResponseEntity.ok(response);
    }
}