package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rental {

    private int id;
    private int carId;
    private int clientId;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;

    public Rental() {
    }

    public int getId() {
        return id;
    }

    public int getCarId() {
        return carId;
    }

    public int getClientId() {
        return clientId;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "ID: " + id + " car ID: " + carId + " client ID " + clientId + " rental date: " + rentalDate.format(dateTimeFormatter) + " return date: " + returnDate.format(dateTimeFormatter) + " \n";
    }
}
