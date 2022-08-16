package com.trainee.app.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars", schema = "public", catalog = "servicedb")
public class CarEntity {
    public CarEntity() {}
    public CarEntity(Long id, String vendor, String model, Integer horsepower, PersonEntity owner) {
        this.id = id;
        this.vendor = vendor;
        this.model = model;
        this.horsepower = horsepower;
        this.owner = owner;
    }

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "vendor", nullable = false)
    private String vendor;


    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "horsepower", nullable = false)
    private Integer horsepower;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private PersonEntity owner;


    public PersonEntity getOwner() {
        return owner;
    }

    public void setOwner(PersonEntity owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return id.equals(carEntity.id) && vendor.equals(carEntity.vendor) && model.equals(carEntity.model) && horsepower.equals(carEntity.horsepower) && owner.equals(carEntity.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vendor, model, horsepower, owner);
    }
}
