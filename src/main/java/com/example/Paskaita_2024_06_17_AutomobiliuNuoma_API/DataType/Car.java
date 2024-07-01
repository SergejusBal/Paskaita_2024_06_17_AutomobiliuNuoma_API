package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType;

import java.time.LocalDate;

public class Car {

    private int id;
    private String make;
    private String model;
    private LocalDate year;
    private Boolean availability;

    public Car() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public LocalDate getYear() {
        return year;
    }

    public Boolean isAvailability() {
        return availability;
    }
    @Override
    public String toString() {
        return "ID: " + id + " make: " + make + " model: " + model + " year " + year.getYear() + " availability: " + availability + " \n";
    }

}
