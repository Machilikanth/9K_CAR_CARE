package com.kcarcare.controller;

import com.kcarcare.dto.AddServiceRequest;
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

    @PostMapping("/{carId}/services")
    public ResponseEntity<Void> addServices(@PathVariable Long carId, @RequestBody AddServiceRequest request) {
        carAdminService.addServicesToCar(carId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{carId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long carId, @RequestBody UpdateStatusRequest request) {
        carAdminService.updateCarStatus(carId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carAdminService.getAllCars();
        return ResponseEntity.ok(cars);
    }
}