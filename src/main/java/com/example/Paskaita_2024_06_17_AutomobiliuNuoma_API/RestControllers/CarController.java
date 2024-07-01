package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.RestControllers;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Car;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    private final CarsService carsService;
    @Autowired
    public CarController(CarsService carsService) {
        this.carsService = carsService;
    }
    @GetMapping("/cars")
    public String getAllCars() {
        return carsService.getAllCars();
    }
    @GetMapping("/cars/available")
    public String getAllAvailableCars() {
        return carsService.getAllAvailableCars();
    }
    @GetMapping("/cars/make/{make}")
    public String getAllCarsByMake(@PathVariable String make){
        return carsService.getAllCarsByMake(make);
    }
    @GetMapping("/cars/year/{year}")
    public String getAllCarsByYear(@PathVariable String year){
        return carsService.getAllCarsByYear(year);
    }
    @GetMapping("/cars/rented-more-than/{count}")
    public String getCarsRentedMoreThan(@PathVariable Integer count){
        return carsService.getCarsRentedMoreThan(count);
    }
    @GetMapping("/cars/{id}")
    public String getCarById(@PathVariable Integer id){
        return carsService.getCarById(id);
    }
    @GetMapping ("/cars/{id}/{period}")
    public String checkAvailableCar(@PathVariable Integer id,@PathVariable String period){
        return carsService.checkAvailableCar(id,period);
    }
    @PostMapping ("/cars")
    public String createCar(@RequestBody Car car){
        return carsService.createCar(car);
    }
    @PutMapping ("/cars/{id}")
    public String alterCar(@RequestBody Car car, @PathVariable Integer id){
        return carsService.alterCar(car,id);
    }
    @DeleteMapping ("/cars/{id}")
    public String deleteCar(@PathVariable Integer id){
        return carsService.deleteCar(id);
    }

}
