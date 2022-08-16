package com.trainee.app.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.trainee.app.utils.validation.DateDeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PersonDTO {

    public PersonDTO(Long id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public PersonDTO() {}

    @NotNull
    @Min(Long.MIN_VALUE)
    @Max(Long.MAX_VALUE)
    private Long id;

    @NotNull
    private String name;

    @JsonFormat(pattern="dd.MM.yyyy")
    @NotNull
    @Past
    @DateTimeFormat(pattern = "dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE)
    @JsonDeserialize(using = DateDeSerializer.class)
    private Date birthdate;


    private List<CarDTO> cars;

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO person = (PersonDTO) o;
        return id.equals(person.id) && name.equals(person.name) && birthdate.equals(person.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate);
    }
}
