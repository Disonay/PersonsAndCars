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
    CarRepository carDao;

    @Autowired
    PersonRepository personDao;

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
                "BMW-X5",
                14,
                person1);
        car2 = new CarEntity(
                2L,
                "AUDI-A6",
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
        Assertions.assertTrue(carDao.findById(1L).isPresent());
        Assertions.assertEquals(car1, carDao.findById(1L).get());

        Assertions.assertTrue(carDao.findById(2L).isPresent());
        Assertions.assertEquals(car2, carDao.findById(2L).get());
    }

    @Test
    public void testFindNotExistedCarById() {
        Assertions.assertTrue(carDao.findById(3L).isEmpty());
    }

    @Test
    public void testFindExistedPersonById() {
        Assertions.assertTrue(personDao.findById(1L).isPresent());
        Assertions.assertEquals(person1, personDao.findById(1L).get());

        Assertions.assertTrue(personDao.findById(2L).isPresent());
        Assertions.assertEquals(person2, personDao.findById(2L).get());
    }

    @Test
    public void testFindNotExistedPersonById() {
        Assertions.assertTrue(personDao.findById(3L).isEmpty());
    }

    @Test
    public void testGetAllCars() {
        List<CarEntity> cars = List.of(car1, car2);

        Assertions.assertIterableEquals(cars, carDao.findAll());
    }

    @Test
    public void testGetAllPersons() {
        List<PersonEntity> persons = List.of(person1, person2);

        Assertions.assertIterableEquals(persons, personDao.findAll());
    }

    @Test
    public void testClearPersons() {
        personDao.deleteAll();

        Assertions.assertEquals(0, personDao.findAll().size());
        Assertions.assertEquals(0, carDao.findAll().size());
    }

    @Test
    public void testSavePerson() throws ParseException {
        PersonEntity newPerson = new PersonEntity(
                3L,
                "Василий",
                new SimpleDateFormat("dd.MM.yyyy").parse("24.12.2001")
        );

        Assertions.assertDoesNotThrow(() -> {
            personDao.save(newPerson);
        });

        Assertions.assertTrue(personDao.findById(3L).isPresent());
        Assertions.assertEquals(newPerson, personDao.findById(3L).get());
    }

    @Test
    public void testSaveCar() throws ParseException {
        CarEntity newCar = new CarEntity(
                3L,
                "BMW-X5",
                14,
                person2
        );

        Assertions.assertDoesNotThrow(() -> {
            carDao.save(newCar);
        });

        Assertions.assertTrue(carDao.findById(3L).isPresent());
        Assertions.assertEquals(newCar, carDao.findById(3L).get());
    }
}
