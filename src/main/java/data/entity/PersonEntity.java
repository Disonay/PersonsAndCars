package data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import data.dto.PersonTO;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "persons", schema = "public", catalog = "servicedb")
@Validated
public class PersonEntity {
    public PersonEntity() {

    }

    public PersonEntity(PersonTO person) {
        setId(person.getId());
        setBirthdate(person.getBirthdate());
        setName(person.getName());
    }
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = -1)
    private String name;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd.MM.yyyy")
    @Column(name = "birthdate", nullable = false)
    private Date birthdate;


    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<CarEntity> cars = new ArrayList<>();

    public Collection<CarEntity> getCars() {
        return cars;
    }

    public void setCars(Collection<CarEntity> cars) {
        this.cars = cars;
    }

    public long getId() {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonEntity that = (PersonEntity) o;
        if (id != that.id)
            return false;
        if (!Objects.equals(name, that.name))
            return false;
        if (!Objects.equals(birthdate, that.birthdate))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate);
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", cars=" + cars +
                '}';
    }
}
