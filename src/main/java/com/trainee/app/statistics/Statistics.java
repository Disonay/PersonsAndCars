package com.trainee.app.statistics;

import java.util.Objects;

public class Statistics {
    private Long personCount;
    private Long carCount;
    private Long uniqueVendorCount;

    public Statistics(Long personCount, Long carCount, Long uniqueVendorCount) {
        this.personCount = personCount;
        this.carCount = carCount;
        this.uniqueVendorCount = uniqueVendorCount;
    }

    public Long getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Long personCount) {
        this.personCount = personCount;
    }

    public Long getCarCount() {
        return carCount;
    }

    public void setCarCount(Long carCount) {
        this.carCount = carCount;
    }

    public Long getUniqueVendorCount() {
        return uniqueVendorCount;
    }

    public void setUniqueVendorCount(Long uniqueVendorCount) {
        this.uniqueVendorCount = uniqueVendorCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(personCount, that.personCount) &&
                Objects.equals(carCount, that.carCount) &&
                Objects.equals(uniqueVendorCount, that.uniqueVendorCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personCount, carCount, uniqueVendorCount);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "personCount=" + personCount +
                ", carCount=" + carCount +
                ", uniqueVendorCount=" + uniqueVendorCount +
                '}';
    }
}
