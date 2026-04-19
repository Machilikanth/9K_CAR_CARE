package com.kcarcare.controller;

import com.kcarcare.dto.CreateCarRequest;
import com.kcarcare.dto.UpdateStatusRequest;
import com.kcarcare.entity.Car;
import com.kcarcare.service.CarAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cars")
public class CarAdminController {

    @Autowired
    private CarAdminService carAdminService;

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody CreateCarRequest request) {
        Car car = carAdminService.createCar(request);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/number/{carNumber}/status")
    public ResponseEntity<Car> updateStatus(@PathVariable String carNumber, @RequestBody UpdateStatusRequest request) {
        Car car = carAdminService.updateCarStatus(carNumber, request);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carAdminService.getAllCars();
        return ResponseEntity.ok(cars);
    }
}