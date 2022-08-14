package com.trainee.app.data.repository;

import com.trainee.app.data.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
    @Override
    List<PersonEntity> findAll();
}
