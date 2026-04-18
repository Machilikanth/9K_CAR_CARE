package com.kcarcare.repository;

import com.kcarcare.entity.CarHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarHistoryRepository extends JpaRepository<CarHistory, Long> {
}