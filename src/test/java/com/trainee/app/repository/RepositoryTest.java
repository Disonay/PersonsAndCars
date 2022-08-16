package com.trainee.app.repository;

import com.trainee.app.data.entity.CarEntity;
import com.trainee.app.data.entity.PersonEntity;
import com.trainee.app.data.repository.CarRepository;
import com.trainee.app.data.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TestEntityManager testEntityManager;

    static PersonEntity person1;
    static PersonEntity person2;
    static CarEntity car1;
    static CarEntity car2;

    @BeforeEach
    void setUp() throws ParseException {
        person1 = new PersonEntity(
                1L,
                "Сергей",
                new SimpleDateFormat("dd.MM.yyyy").parse("25.10.2001"));
        person2 = new PersonEntity(
                2L,
                "Антон",
                new SimpleDateFormat("dd.MM.yyyy").parse("24.10.2001"));
        car1 = new CarEntity(
                1L,
                "BMW",
                "X5",
                14,
                person1);
        car2 = new CarEntity(
                2L,
                "BMW",
                "X6",
                14,
                person1);
        person1.setCars(List.of(car1, car2));

        testEntityManager.persistAndFlush(person1);
        testEntityManager.persistAndFlush(person2);
        testEntityManager.persistAndFlush(car1);
        testEntityManager.persistAndFlush(car2);
    }

    @Test
    public void testFindExistedCarById() {
        Assertions.assertTrue(carRepository.findById(1L).isPresent());
        Assertions.assertTrue(carRepository.existsById(1L));
        Assertions.assertEquals(car1, carRepository.findById(1L).get());

        Assertions.assertTrue(carRepository.findById(2L).isPresent());
        Assertions.assertTrue(carRepository.existsById(2L));
        Assertions.assertEquals(car2, carRepository.findById(2L).get());
    }

    @Test
    public void testFindNotExistedCarById() {
        Assertions.assertFalse(carRepository.existsById(3L));
        Assertions.assertTrue(carRepository.findById(3L).isEmpty());
    }

    @Test
    public void testFindExistedPersonById() {
        Assertions.assertTrue(personRepository.findById(1L).isPresent());
        Assertions.assertTrue(personRepository.existsById(1L));
        Assertions.assertEquals(person1, personRepository.findById(1L).get());

        Assertions.assertTrue(personRepository.findById(2L).isPresent());
        Assertions.assertTrue(personRepository.existsById(2L));
        Assertions.assertEquals(person2, personRepository.findById(2L).get());
    }

    @Test
    public void testFindNotExistedPersonById() {
        Assertions.assertFalse(personRepository.existsById(3L));
        Assertions.assertTrue(personRepository.findById(3L).isEmpty());
    }

    @Test
    public void testClearPersons() {
        personRepository.deleteAll();

        Assertions.assertEquals(0, personRepository.findAll().size());
        Assertions.assertEquals(0, carRepository.findAll().size());
    }

    @Test
    public void testSavePerson() throws ParseException {
        PersonEntity newPerson = new PersonEntity(
                3L,
                "Василий",
                new SimpleDateFormat("dd.MM.yyyy").parse("24.12.2001")
        );

        Assertions.assertDoesNotThrow(() -> {
            personRepository.save(newPerson);
        });

        Assertions.assertTrue(personRepository.findById(3L).isPresent());
        Assertions.assertTrue(personRepository.existsById(3L));
        Assertions.assertEquals(newPerson, personRepository.findById(3L).get());
    }

    @Test
    public void testSaveCar() {
        CarEntity newCar = new CarEntity(
                3L,
                "BMW",
                "X5",
                14,
                person2
        );

        Assertions.assertDoesNotThrow(() -> {
            carRepository.save(newCar);
        });

        Assertions.assertTrue(carRepository.findById(3L).isPresent());
        Assertions.assertTrue(carRepository.existsById(3L));
        Assertions.assertEquals(newCar, carRepository.findById(3L).get());
    }

    @Test
    public void getPersonsCount() {
        Assertions.assertEquals(2, personRepository.count());
    }

    @Test
    public void getCarsCount() {
        Assertions.assertEquals(2, carRepository.count());
    }

    @Test
    public void getDistinctCarVendor() {
        Assertions.assertEquals(1, carRepository.countDistinctByVendor());
    }
}
