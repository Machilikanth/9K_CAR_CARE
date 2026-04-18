package com.kcarcare.repository;

import com.kcarcare.entity.Car;
import com.kcarcare.entity.CarService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarServiceRepository extends JpaRepository<CarService, Long> {

    List<CarService> findByCar(Car car);
}