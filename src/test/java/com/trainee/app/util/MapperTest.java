package com.trainee.app.util;

import com.trainee.app.data.dto.CarDTO;
import com.trainee.app.data.dto.PersonDTO;
import com.trainee.app.data.entity.CarEntity;
import com.trainee.app.data.entity.PersonEntity;
import com.trainee.app.utils.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MapperTest {
    static PersonEntity personEntity;
    static CarEntity carEntity;
    static CarDTO carDTO;
    static PersonDTO personDTO;

    @BeforeAll
    public static void setUp() throws ParseException {
        personEntity = new PersonEntity(
                1L,
                "Сергей",
                new SimpleDateFormat("dd.MM.yyyy").parse("25.10.2001"));
        carEntity = new CarEntity(
                1L,
                "BMW",
                "X5",
                14,
                personEntity);

        personDTO = new PersonDTO(1L,
                "Сергей",
                new SimpleDateFormat("dd.MM.yyyy").parse("25.10.2001"));
        carDTO = new CarDTO(1L, "BMW-X5", 14, 1L);
    }

    @Test
    public void testPersonEntityToPersonDTO() {
        Assertions.assertEquals(personDTO, Mapper.fromPersonEntityToPerson(personEntity));
    }

    @Test
    public void testPersonToPersonEntity() {
        Assertions.assertEquals(personEntity, Mapper.fromPersonToPersonEntity(personDTO));
    }

    @Test
    public void testCarEntityToCarDTO() {
        Assertions.assertEquals(carDTO, Mapper.fromCarEntityToCar(carEntity));
    }

    @Test
    public void testCarToCarEntity() {
        Assertions.assertEquals(carEntity, Mapper.fromCarToCarEntity(carDTO, personEntity));
    }

}
