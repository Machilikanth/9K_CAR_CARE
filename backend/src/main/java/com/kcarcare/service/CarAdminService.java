package com.kcarcare.service;

import com.kcarcare.dto.CreateCarRequest;
import com.kcarcare.dto.CustomerStatusResponseDTO;
import com.kcarcare.dto.UpdateStatusRequest;
import com.kcarcare.entity.Car;
import com.kcarcare.entity.CarHistory;
import com.kcarcare.entity.CarService;
import com.kcarcare.entity.CarStatus;
import com.kcarcare.entity.ServiceEntity;
import com.kcarcare.entity.ServiceType;
import com.kcarcare.repository.CarHistoryRepository;
import com.kcarcare.repository.CarRepository;
import com.kcarcare.repository.CarServiceRepository;
import com.kcarcare.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CarAdminService {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10}$");

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CarServiceRepository carServiceRepository;

    @Autowired
    private CarHistoryRepository carHistoryRepository;

    @Transactional
    public Car createCar(CreateCarRequest request) {
        validateCreateRequest(request);

        String carNumber = request.getCarNumber().trim().toUpperCase();
        ensureNoActiveRequest(carNumber);

        Car car = new Car();
        car.setCarNumber(carNumber);
        car.setCustomerName(request.getCustomerName().trim());
        car.setCustomerMobile(request.getCustomerMobile().trim());
        car.setWorkerName(request.getWorkerName().trim());
        car.setWorkerMobile(request.getWorkerMobile().trim());
        car.setStatus(CarStatus.RECEIVED);
        car.setExpectedDeliveryTime(request.getExpectedDeliveryTime());
        car = carRepository.save(car);

        assignServicesToCar(car, null, request.getServiceNames());

        CarHistory history = new CarHistory();
        history.setCar(car);
        history.setStatus(CarStatus.RECEIVED);
        history.setRemarks("Car request created");
        carHistoryRepository.save(history);

        return car;
    }

    @Transactional
    public Car updateCarStatus(String carNumber, UpdateStatusRequest request) {
        Car car = carRepository.findFirstByCarNumberIgnoreCaseOrderByCreatedAtDesc(carNumber)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        CarStatus newStatus = CarStatus.valueOf(request.getStatus().toUpperCase());
        validateStatusTransition(car.getStatus(), newStatus);

        car.setStatus(newStatus);
        carRepository.save(car);

        CarHistory history = new CarHistory();
        history.setCar(car);
        history.setStatus(newStatus);
        history.setRemarks(request.getRemarks());
        carHistoryRepository.save(history);

        return car;
    }

    public CustomerStatusResponseDTO getCustomerStatus(String carNumber) {
        Car car = carRepository.findFirstByCarNumberIgnoreCaseOrderByCreatedAtDesc(carNumber)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        List<CarService> carServices = carServiceRepository.findByCar(car);
        List<String> services = carServices.stream()
                .map(cs -> cs.getService().getName())
                .collect(Collectors.toList());

        CustomerStatusResponseDTO response = new CustomerStatusResponseDTO();
        response.setCarNumber(car.getCarNumber());
        response.setCustomerName(car.getCustomerName());
        response.setServiceTypes(services);
        response.setStatus(car.getStatus().name());
        response.setWorkerName(car.getWorkerName());
        response.setEstimatedDeliveryTime(car.getExpectedDeliveryTime());
        if (car.getStatus() == CarStatus.COMPLETED) {
            response.setMessage("Please collect your car");
        }
        return response;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    private void validateCreateRequest(CreateCarRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create request cannot be null");
        }
        if (request.getCarNumber() == null || request.getCarNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Car number is required");
        }
        if (request.getCustomerName() == null || request.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (!isValidMobile(request.getCustomerMobile())) {
            throw new IllegalArgumentException("Customer mobile number must be 10 digits");
        }
        if (request.getWorkerName() == null || request.getWorkerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Worker name is required");
        }
        if (!isValidMobile(request.getWorkerMobile())) {
            throw new IllegalArgumentException("Worker mobile number must be 10 digits");
        }
        if (request.getExpectedDeliveryTime() == null || request.getExpectedDeliveryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expected delivery time is required and must be in the future");
        }
    }

    private boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile.trim()).matches();
    }

    private void ensureNoActiveRequest(String carNumber) {
        Optional<Car> existing = carRepository.findFirstByCarNumberIgnoreCaseOrderByCreatedAtDesc(carNumber);
        if (existing.isPresent()) {
            Car car = existing.get();
            if (car.getStatus() != CarStatus.COMPLETED) {
                throw new RuntimeException("An active request already exists for this car number");
            }
        }
    }

    private void validateStatusTransition(CarStatus currentStatus, CarStatus newStatus) {
        if (currentStatus == newStatus) {
            return;
        }
        if (currentStatus == CarStatus.RECEIVED && newStatus == CarStatus.IN_PROGRESS) {
            return;
        }
        if (currentStatus == CarStatus.IN_PROGRESS && newStatus == CarStatus.COMPLETED) {
            return;
        }
        throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
    }

    private void assignServicesToCar(Car car, List<Long> serviceIds, List<String> serviceNames) {
        if (serviceNames != null && !serviceNames.isEmpty()) {
            for (String serviceName : serviceNames) {
                if (serviceName == null || serviceName.isBlank()) {
                    continue;
                }
                String normalizedName = normalizeServiceName(serviceName);
                ServiceEntity service = serviceRepository.findByNameIgnoreCase(normalizedName)
                        .orElseGet(() -> createServiceEntity(normalizedName));
                saveCarService(car, service);
            }
        }
    }

    private String normalizeServiceName(String serviceName) {
        ServiceType serviceType = ServiceType.fromString(serviceName);
        if (serviceType != null) {
            return serviceType.name();
        }
        return serviceName.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    private ServiceEntity createServiceEntity(String serviceName) {
        ServiceEntity service = new ServiceEntity();
        service.setName(serviceName);
        service.setPrice(BigDecimal.ZERO);
        return serviceRepository.save(service);
    }

    private void saveCarService(Car car, ServiceEntity service) {
        CarService carService = new CarService();
        carService.setCar(car);
        carService.setService(service);
        carServiceRepository.save(carService);
    }
}
