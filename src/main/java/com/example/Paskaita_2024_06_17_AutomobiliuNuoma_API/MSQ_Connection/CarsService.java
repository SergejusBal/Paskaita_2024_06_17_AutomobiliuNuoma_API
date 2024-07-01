package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataBaseRepository.CarDataBaseRepository;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CarsService {
    private final CarDataBaseRepository carDataBaseRepository;

    @Autowired
    public CarsService(CarDataBaseRepository carDataBaseRepository) {
        this.carDataBaseRepository = carDataBaseRepository;
    }

    public String getAllCars(){
        return carDataBaseRepository.getAllCars();
    }

    public String getCarsRentedMoreThan(Integer count){
        return carDataBaseRepository.getCarsRentedMoreThan(count);
    }


    public String getAllAvailableCars(){
        return carDataBaseRepository.getAllAvailableCars();
    }

   public String checkAvailableCar(Integer id,String period){
       LocalDate date = formatDate(period);
       if (date.getYear() == 1900) return "Invalid input";
       if(carDataBaseRepository.checkAvailableCar(id,period)) return "Available";
       return "Unavailable";
   }

    public String getCarById(Integer id){
        return carDataBaseRepository.getCarById(id);
    }

    public String getAllCarsByMake(String make){
        return carDataBaseRepository.getAllCarsByMake(make);
    }

    public String getAllCarsByYear(String shearchedYear){
        return carDataBaseRepository.getAllCarsByYear(shearchedYear);
    }
//
//
    public String createCar(Car car){
        if(
                car.getMake() == null || car.getModel() == null ||
                car.getYear() == null || car.isAvailability() == null
        )
        {
            return "Invalid Input";
        }

        return carDataBaseRepository.createCar(car);
    }
    public String alterCar(Car car,Integer id){

        if(
                car.getMake() == null || car.getModel() == null ||
                car.getYear() == null || car.isAvailability() == null
        )
        {
            return "Invalid Input";
        }
        return carDataBaseRepository.alterCar(car,id);
    }

    public String deleteCar(Integer id){
        carDataBaseRepository.deleteRentalByCarID(id);
        return carDataBaseRepository.deleteCar(id);
    }

    private LocalDateTime formatDateTime(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime rentalDate = null;
        try {
            rentalDate = LocalDateTime.parse(dateTime, dateTimeFormatter);
        }catch(DateTimeParseException | NullPointerException e) {
            rentalDate = LocalDateTime.parse("2000-01-01 00:00:00", dateTimeFormatter);
        }
        return rentalDate;
    }
    private LocalDate formatDate(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate year = null;
        try {
            year = LocalDate.parse(dateTime, dateTimeFormatter);
        }catch(DateTimeParseException | NullPointerException e) {
            year = LocalDate.parse("1900-01-01",dateTimeFormatter);
        }
        return year;
    }



}
