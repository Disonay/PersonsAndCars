package data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import data.dto.CarTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars", schema = "public", catalog = "servicedb")
public class CarEntity {
    public CarEntity(CarTO car){
        setId(car.getId());
        setModel(car.getModel());
        setHorsepower(car.getHorsepower());
    }
    public CarEntity() {

    }
    @Id
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "model", nullable = false, length = -1)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarEntity that = (CarEntity) o;

        if (!Objects.equals(id, that.id))
            return false;
        if (!Objects.equals(model, that.model))
            return false;
        if (!Objects.equals(horsepower, that.horsepower))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, horsepower);
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", horsepower=" + horsepower +
                '}';
    }
}
