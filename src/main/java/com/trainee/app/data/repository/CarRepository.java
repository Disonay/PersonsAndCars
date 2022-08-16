package com.trainee.app.data.repository;

import com.trainee.app.data.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
    @Override
    List<CarEntity> findAll();

    @Query(nativeQuery = true, value = "select count(distinct vendor) from cars")
    Long countDistinctByVendor();
}
