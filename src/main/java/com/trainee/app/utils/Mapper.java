package com.trainee.app.utils;

import com.trainee.app.data.dto.Car;
import com.trainee.app.data.dto.Person;
import com.trainee.app.data.entity.CarEntity;
import com.trainee.app.data.entity.PersonEntity;

public class Mapper {
    public static PersonEntity fromPersonToPersonEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setBirthdate(person.getBirthdate());

        return entity;
    }

    public static Person fromPersonEntityToPerson(PersonEntity entity) {
        Person person = new Person(entity.getId(), entity.getName(), entity.getBirthdate());
        person.setCars(entity.getCars().stream().map(Mapper::fromCarEntityToCar).toList());

        return person;
    }

    public static CarEntity fromCarToCarEntity(Car car, PersonEntity owner) {
        CarEntity entity = new CarEntity();
        entity.setId(car.getId());
        entity.setModel(car.getModel());
        entity.setOwner(owner);
        entity.setHorsepower(car.getHorsepower());

        return entity;
    }

    public static Car fromCarEntityToCar(CarEntity entity) {
        return new Car(entity.getId(), entity.getModel(), entity.getHorsepower(), entity.getOwner().getId());
    }
}
