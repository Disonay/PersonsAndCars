package com.trainee.app.data.repository;

import com.trainee.app.data.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
    @Override
    List<CarEntity> findAll();
}
