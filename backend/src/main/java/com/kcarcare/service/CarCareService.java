package com.kcarcare.service;

import com.kcarcare.entity.Car;
import com.kcarcare.entity.CarService;
import com.kcarcare.repository.CarRepository;
import com.kcarcare.repository.CarServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarCareService {

    @Autowired
    private CarServiceRepository carServiceRepository;

    @Autowired
    private CarRepository carRepository;

    public BigDecimal calculateTotal(Long carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Car car = carOpt.get();
        List<CarService> services = carServiceRepository.findByCar(car);

        return services.stream()
            .map(cs -> cs.getService().getPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}