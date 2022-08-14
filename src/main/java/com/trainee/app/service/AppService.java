package com.trainee.app.service;

import com.trainee.app.data.repository.CarRepository;
import com.trainee.app.data.repository.PersonRepository;
import com.trainee.app.exceptions.BadCarException;
import com.trainee.app.statistics.Statistics;
import com.trainee.app.utils.validation.LegalBirthdateValidation;
import com.trainee.app.data.dto.Car;
import com.trainee.app.data.dto.Person;
import com.trainee.app.data.entity.CarEntity;
import com.trainee.app.data.entity.PersonEntity;
import com.trainee.app.exceptions.PersonAlreadyExistsException;
import com.trainee.app.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trainee.app.utils.Mapper;

import java.util.List;
import java.util.Optional;

@Service
public class AppService {
    private final CarRepository carDAO;
    private final PersonRepository personDAO;

    public AppService(@Autowired CarRepository carDAO, @Autowired PersonRepository personDAO) {
        this.carDAO = carDAO;
        this.personDAO = personDAO;
    }

    public void savePerson(Person person) {
        if (personDAO.findById(person.getId()).isEmpty()) {
            personDAO.save(Mapper.fromPersonToPersonEntity(person));
        }
        else {
            throw new PersonAlreadyExistsException();
        }
    }

    public void saveCar(Car car) {
        Optional<PersonEntity> optionalOwner = personDAO.findById(car.getOwnerId());

        if (optionalOwner.isPresent()) {
            PersonEntity owner = optionalOwner.get();
            if (LegalBirthdateValidation.getYearsTillNow(owner.getBirthdate()) >= 18) {
                if (carDAO.findById(car.getId()).isEmpty()) {
                    carDAO.save(Mapper.fromCarToCarEntity(car, owner));
                }
                else {
                    throw new BadCarException();
                }
            }
            else {
                throw new BadCarException();
            }
        }
        else {
            throw new BadCarException();
        }
    }

    public Person getPerson(Long personId) {
        Optional<PersonEntity> optionalPerson = personDAO.findById(personId);
        if (optionalPerson.isPresent()) {
            return Mapper.fromPersonEntityToPerson(optionalPerson.get());
        }
        else {
            throw new PersonNotFoundException();
        }
    }

    public Statistics getStatistics() {
        List<CarEntity> cars = carDAO.findAll();

        Long personCount = (long) personDAO.findAll().size();
        Long carCount = (long) cars.size();
        Long uniqueVendorCount = cars.stream().map(CarEntity::getModel).distinct().count();

        return new Statistics(personCount, carCount, uniqueVendorCount);
    }

    public void deleteAll() {
        personDAO.deleteAll();
    }
}
