package controller;

import data.dao.DAO;
import data.dao.hibernate.car.CarHibernateDAO;
import data.dao.hibernate.person.PersonHibernateDAO;
import data.dto.CarTO;
import data.dto.PersonTO;
import data.entity.CarEntity;
import data.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import statistics.Statistics;
import utils.DateValidation;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class AppController {
    private final DAO<CarEntity> carDAO;
    private final DAO<PersonEntity> personDAO;

    public AppController(@Autowired CarHibernateDAO carDAO, @Autowired PersonHibernateDAO personDAO) {
        this.carDAO = carDAO;
        this.personDAO = personDAO;
    }

    @PostMapping(value = "/person")
    void addPerson(@Valid @RequestBody PersonTO person) throws SQLException {
        personDAO.save(new PersonEntity(person));
    }

    @PostMapping(value = "/car")
    void addCar(@Valid @RequestBody CarTO car) throws SQLException {
        CarEntity newCar = new CarEntity(car);
        PersonEntity owner = personDAO.findById(car.getOwnerId());

        if (DateValidation.getYearsTillNow(owner.getBirthdate()) >= 18) {
            newCar.setOwner(personDAO.findById(car.getOwnerId()));
            carDAO.save(newCar);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/personwithcars")
    PersonEntity getPersonWithCars(@RequestParam("personId") Long personId) throws SQLException {
        PersonEntity person = personDAO.findById(personId);

        if (Objects.isNull(person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return personDAO.findById(personId);
    }

    @GetMapping("/statistics")
    Statistics getStatistics() throws SQLException {
        List<CarEntity> cars = carDAO.getAll();

        Statistics statistics = new Statistics();
        statistics.setPersonCount((long) personDAO.getAll().size());
        statistics.setCarCount((long) cars.size());
        statistics.setUniqueVendorCount(cars.stream().map(CarEntity::getModel).distinct().count());

        return statistics;
    }

    @GetMapping("/clear")
    void clear() {
        try {
            carDAO.clear();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        try {
            personDAO.clear();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
