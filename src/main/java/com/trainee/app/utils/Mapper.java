package com.trainee.app.utils;

import com.trainee.app.data.dto.CarDTO;
import com.trainee.app.data.dto.PersonDTO;
import com.trainee.app.data.entity.CarEntity;
import com.trainee.app.data.entity.PersonEntity;

public class Mapper {
    public static PersonEntity fromPersonToPersonEntity(PersonDTO person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setBirthdate(person.getBirthdate());

        return entity;
    }

    public static PersonDTO fromPersonEntityToPerson(PersonEntity entity) {
        PersonDTO person = new PersonDTO(entity.getId(), entity.getName(), entity.getBirthdate());
        person.setCars(entity.getCars().stream().map(Mapper::fromCarEntityToCar).toList());

        return person;
    }

    public static CarEntity fromCarToCarEntity(CarDTO car, PersonEntity owner) {
        CarEntity entity = new CarEntity();
        entity.setId(car.getId());
        String[] vendorAndModelSeparately = car.getModel().split("-");
        entity.setVendor(vendorAndModelSeparately[0]);
        entity.setModel(vendorAndModelSeparately[1]);
        entity.setOwner(owner);
        entity.setHorsepower(car.getHorsepower());

        return entity;
    }

    public static CarDTO fromCarEntityToCar(CarEntity entity) {
        return new CarDTO(
                entity.getId(),
                entity.getVendor() + "-" + entity.getModel(),
                entity.getHorsepower(),
                entity.getOwner().getId());
    }
}
