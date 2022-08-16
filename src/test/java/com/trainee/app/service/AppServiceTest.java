package com.trainee.app.service;

import com.trainee.app.data.dto.CarDTO;
import com.trainee.app.data.dto.PersonDTO;
import com.trainee.app.data.entity.CarEntity;
import com.trainee.app.data.entity.PersonEntity;
import com.trainee.app.data.repository.CarRepository;
import com.trainee.app.data.repository.PersonRepository;
import com.trainee.app.exceptions.BadCarException;
import com.trainee.app.exceptions.PersonAlreadyExistsException;
import com.trainee.app.exceptions.PersonNotFoundException;
import com.trainee.app.statistics.Statistics;
import com.trainee.app.utils.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class AppServiceTest {
    @Mock(name = "carDAO")
    private CarRepository carDAO;

    @Mock(name = "personDAO")
    private PersonRepository personDAO;

    @InjectMocks
    private AppService appService;

    static PersonDTO person;
    static PersonDTO illegalPerson;
    static PersonEntity personEntity;

    static PersonEntity illegalPersonEntity;
    static CarDTO car;
    static CarEntity carEntity;

    MockitoSession session;

    @Before
    public void beforeMethod() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
    }


    @After
    public void afterMethod() {
        session.finishMocking();
    }

    @BeforeAll
    public static void setUp() throws ParseException {
        person = new PersonDTO(1L, "Сергей", new SimpleDateFormat("dd.MM.yyyy").parse("25.10.2001"));
        illegalPerson = new PersonDTO(1L, "Сергей", new SimpleDateFormat("dd.MM.yyyy").parse("25.10.2012"));
        car = new CarDTO(1L, "BWM-X5", 14, 1L);
        person.setCars(List.of(car));

        personEntity = Mapper.fromPersonToPersonEntity(person);
        illegalPersonEntity = Mapper.fromPersonToPersonEntity(illegalPerson);
        carEntity = Mapper.fromCarToCarEntity(car, personEntity);
    }

    @Test
    public void testSaveNewPerson() {
        Mockito.when(personDAO.existsById(person.getId())).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> {
            appService.savePerson(person);
        });
    }

    @Test
    public void testSaveExistedPerson() {
        Mockito.when(personDAO.existsById(person.getId())).thenReturn(true);

        Assertions.assertThrows(PersonAlreadyExistsException.class, () -> {
            appService.savePerson(person);
        });
    }

    @Test
    public void testSaveNewCorrectCar() {
        Mockito.when(personDAO.findById(person.getId())).thenReturn(Optional.of(personEntity));
        Mockito.when(carDAO.existsById(car.getId())).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> {
            appService.saveCar(car);
        });
    }

    @Test
    public void testSaveExistedCar() {
        Mockito.when(personDAO.findById(car.getOwnerId())).thenReturn(Optional.of(personEntity));
        Mockito.when(carDAO.existsById(car.getId())).thenReturn(true);

        Assertions.assertThrows(BadCarException.class, () -> {
            appService.saveCar(car);
        });
    }

    @Test
    public void testSaveCarWithIllegalOwner() {
        Mockito.when(personDAO.findById(car.getOwnerId())).thenReturn(Optional.of(illegalPersonEntity));

        Assertions.assertThrows(BadCarException.class, () -> {
            appService.saveCar(car);
        });
    }

    @Test
    public void testGetExistedPerson() {
        Mockito.when(personDAO.findById(person.getId())).thenReturn(Optional.of(personEntity));

        Assertions.assertEquals(person, appService.getPerson(person.getId()));
    }

    @Test
    public void testGetNotExistedPerson() {
        Mockito.when(personDAO.findById(person.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            appService.getPerson(person.getId());
        });
    }

    @Test
    public void testStatistics() {
        Mockito.when(personDAO.count()).thenReturn(2L);
        Mockito.when(carDAO.count()).thenReturn(1L);
        Mockito.when(carDAO.countDistinctByVendor()).thenReturn(1L);

        Assertions.assertEquals(new Statistics(2L, 1L, 1L), appService.getStatistics());
    }
}
