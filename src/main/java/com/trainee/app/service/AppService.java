package com.trainee.app.service;

import com.trainee.app.data.dto.CarDTO;
import com.trainee.app.data.dto.PersonDTO;
import com.trainee.app.data.entity.PersonEntity;
import com.trainee.app.data.repository.CarRepository;
import com.trainee.app.data.repository.PersonRepository;
import com.trainee.app.exceptions.BadCarException;
import com.trainee.app.exceptions.PersonAlreadyExistsException;
import com.trainee.app.exceptions.PersonNotFoundException;
import com.trainee.app.statistics.Statistics;
import com.trainee.app.utils.Mapper;
import com.trainee.app.utils.validation.LegalBirthdateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService {
    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    public AppService(@Autowired CarRepository carRepository, @Autowired PersonRepository personRepository) {
        this.carRepository = carRepository;
        this.personRepository = personRepository;
    }

    public void savePerson(PersonDTO person) {
        if (personRepository.existsById(person.getId())) {
            throw new PersonAlreadyExistsException();
        }
        personRepository.save(Mapper.fromPersonToPersonEntity(person));
    }

    public void saveCar(CarDTO car) {
        Optional<PersonEntity> optionalOwner = personRepository.findById(car.getOwnerId());
        optionalOwner.orElseThrow(BadCarException::new);

        PersonEntity owner = optionalOwner.get();
        if (LegalBirthdateValidation.getYearsTillNow(owner.getBirthdate()) < 18) {
            throw new BadCarException();
        }

        if (carRepository.existsById(car.getId())) {
            throw new BadCarException();
        }

        carRepository.save(Mapper.fromCarToCarEntity(car, owner));
    }

    public PersonDTO getPerson(Long personId) {
        Optional<PersonEntity> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return Mapper.fromPersonEntityToPerson(optionalPerson.get());
    }

    public Statistics getStatistics() {
        Long personCount = personRepository.count();
        Long carCount = carRepository.count();
        Long uniqueVendorCount = carRepository.countDistinctByVendor();

        return new Statistics(personCount, carCount, uniqueVendorCount);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
