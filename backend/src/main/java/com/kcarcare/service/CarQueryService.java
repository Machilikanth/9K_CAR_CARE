package com.kcarcare.service;

import com.kcarcare.dto.CarResponseDTO;
import com.kcarcare.dto.ServiceDTO;
import com.kcarcare.entity.Car;
import com.kcarcare.entity.CarService;
import com.kcarcare.entity.ServiceEntity;
import com.kcarcare.repository.CarRepository;
import com.kcarcare.repository.CarServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarQueryService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarServiceRepository carServiceRepository;

    public CarResponseDTO getCarDetails(String carNumber) {

        List<Car> cars = carRepository
                .findByCarNumberOrderByCreatedAtDesc(carNumber);

        if (cars.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        Car car = cars.get(0); // latest entry

        List<CarService> carServices = carServiceRepository.findByCar(car);

        List<ServiceDTO> serviceDTOs = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CarService cs : carServices) {
            ServiceEntity service = cs.getService();

            ServiceDTO dto = new ServiceDTO();
            dto.setName(service.getName());
            dto.setPrice(service.getPrice());

            serviceDTOs.add(dto);

            total = total.add(service.getPrice());
        }

        CarResponseDTO response = new CarResponseDTO();
        response.setCarNumber(car.getCarNumber());
        response.setStatus(car.getStatus().name());
        response.setExpectedDeliveryTime(car.getExpectedDeliveryTime());
        response.setServices(serviceDTOs);
        response.setTotalPrice(total);

        return response;
    }
}