package com.kcarcare.service;

import com.kcarcare.dto.AddServiceRequest;
import com.kcarcare.dto.CreateCarRequest;
import com.kcarcare.dto.UpdateStatusRequest;
import com.kcarcare.entity.*;
import com.kcarcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarAdminService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CarServiceRepository carServiceRepository;

    @Autowired
    private CarHistoryRepository carHistoryRepository;

    @Transactional
    public Car createCar(CreateCarRequest request) {
        // Convert carNumber to uppercase
        String carNumber = request.getCarNumber().toUpperCase();

        // Find branch
        Optional<Branch> branchOpt = branchRepository.findById(request.getBranchId());
        if (branchOpt.isEmpty()) {
            throw new RuntimeException("Branch not found");
        }
        Branch branch = branchOpt.get();

        // Create car
        Car car = new Car();
        car.setCarNumber(carNumber);
        car.setStatus(CarStatus.RECEIVED);
        car.setExpectedDeliveryTime(request.getExpectedDeliveryTime());
        car.setBranch(branch);
        car = carRepository.save(car);

        // Assign services
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Long serviceId : request.getServiceIds()) {
                Optional<ServiceEntity> serviceOpt = serviceRepository.findById(serviceId);
                if (serviceOpt.isPresent()) {
                    CarService carService = new CarService();
                    carService.setCar(car);
                    carService.setService(serviceOpt.get());
                    carServiceRepository.save(carService);
                }
            }
        }

        // Add to history
        CarHistory history = new CarHistory();
        history.setCar(car);
        history.setStatus(CarStatus.RECEIVED);
        history.setRemarks("Car created");
        carHistoryRepository.save(history);

        return car;
    }

    @Transactional
    public void addServicesToCar(Long carId, AddServiceRequest request) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new RuntimeException("Car not found");
        }
        Car car = carOpt.get();

        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            for (Long serviceId : request.getServiceIds()) {
                Optional<ServiceEntity> serviceOpt = serviceRepository.findById(serviceId);
                if (serviceOpt.isPresent()) {
                    CarService carService = new CarService();
                    carService.setCar(car);
                    carService.setService(serviceOpt.get());
                    carServiceRepository.save(carService);
                }
            }
        }
    }

    @Transactional
    public void updateCarStatus(Long carId, UpdateStatusRequest request) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new RuntimeException("Car not found");
        }
        Car car = carOpt.get();

        CarStatus newStatus = CarStatus.valueOf(request.getStatus().toUpperCase());
        car.setStatus(newStatus);
        carRepository.save(car);

        // Add to history
        CarHistory history = new CarHistory();
        history.setCar(car);
        history.setStatus(newStatus);
        history.setRemarks(request.getRemarks());
        carHistoryRepository.save(history);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}