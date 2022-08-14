package com.trainee.app.controller;

import com.trainee.app.data.dto.Car;
import com.trainee.app.data.dto.Person;
import com.trainee.app.service.AppService;
import com.trainee.app.statistics.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class AppController {
   private final AppService service;

    public AppController(@Autowired AppService service) {
        this.service = service;
    }

    @PostMapping(value = "/person")
    void addPerson(@Valid @RequestBody Person person)  {
        service.savePerson(person);
    }

    @PostMapping(value = "/car")
    void addCar(@Valid @RequestBody Car car) {
        service.saveCar(car);
    }

    @GetMapping("/personwithcars")
    Person getPersonWithCars(@RequestParam("personId") Long personId)  {
        return service.getPerson(personId);
    }

    @GetMapping("/statistics")
    Statistics getStatistics()  {
        return service.getStatistics();
    }

    @GetMapping("/clear")
    void clear()  {
        service.deleteAll();
    }
}
