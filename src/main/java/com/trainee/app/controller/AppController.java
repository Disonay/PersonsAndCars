package com.trainee.app.controller;

import com.trainee.app.data.dto.CarDTO;
import com.trainee.app.data.dto.PersonDTO;
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
    void addPerson(@Valid @RequestBody PersonDTO person)  {
        service.savePerson(person);
    }

    @PostMapping(value = "/car")
    void addCar(@Valid @RequestBody CarDTO car) {
        service.saveCar(car);
    }

    @GetMapping("/personwithcars")
    PersonDTO getPersonWithCars(@RequestParam("personId") Long personId)  {
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
