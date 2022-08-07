package data.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CarTO {

    @NotNull
    @Min(Long.MIN_VALUE)
    @Max(Long.MAX_VALUE)
    private Long id;

    @Pattern(regexp = "^[a-zA-z0-9]+-[a-zA-z0-9]+")
    @NotNull
    private String model;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @NotNull
    private Integer horsepower;

    @NotNull
    @Min(Long.MIN_VALUE)
    @Max(Long.MAX_VALUE)
    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
