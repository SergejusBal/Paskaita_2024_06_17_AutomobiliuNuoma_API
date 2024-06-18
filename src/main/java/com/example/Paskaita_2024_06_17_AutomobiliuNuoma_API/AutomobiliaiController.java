package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Car;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Client;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Rental;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection.MSQRentals;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection.MSQCars;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection.MSQClients;
import org.springframework.web.bind.annotation.*;

@RestController
public class AutomobiliaiController {
    private final String URL = "jdbc:mysql://localhost:3306/java_data";
    private final String USERNAME = "root";
    private final String PASSWORD = "TestPassword";
    MSQCars msqCars = new MSQCars(URL,USERNAME,PASSWORD);
    MSQClients msqClients = new MSQClients(URL,USERNAME,PASSWORD);
    MSQRentals msqRentals = new MSQRentals(URL,USERNAME,PASSWORD);


    @GetMapping("/cars")
    public String getAllCars(){
        return msqCars.getAllCars();
   }
    @GetMapping("/cars/{id}")
    public String getCarById(@PathVariable Integer id){
        return msqCars.getCarById(id);
    }
    @PostMapping ("/cars")
    public String createCar(@RequestBody Car car){
        return msqCars.createCar(car);
    }
    @PutMapping ("/cars/{id}")
    public String alterCar(@RequestBody Car car, @PathVariable Integer id){
        return msqCars.alterCar(car,id);
    }

    @DeleteMapping ("/cars/{id}")
    public String deleteCar(@PathVariable Integer id){
        return msqCars.deleteCar(id);
    }

    @GetMapping("/clients")
    public String getAllClients(){
        return msqClients.getAllClients();
    }
    @GetMapping("/clients/{id}")
    public String getClientById(@PathVariable Integer id){
        return msqClients.getClientsById(id);
    }
    @PostMapping ("/clients")
    public String createClients(@RequestBody Client client){
        return msqClients.createClient(client);
    }
    @PutMapping ("/clients/{id}")
    public String alterClient(@RequestBody Client client, @PathVariable Integer id){
        return msqClients.alterClient(client,id);
    }

    @DeleteMapping ("/clients/{id}")
    public String deleteClient(@PathVariable Integer id){
        return msqClients.deleteClient(id);
    }

    @GetMapping("/rentals")
    public String getAllRentals(){
        return msqRentals.getAllRentals();
    }
    @GetMapping("/rentals/{id}")
    public String getRentalById(@PathVariable Integer id){
        return msqRentals.getRentalById(id);
    }
    @PostMapping ("/rentals")
    public String createRental(@RequestBody Rental rental){
        return msqRentals.createRental(rental);
    }
    @PutMapping ("/rentals/{id}")
    public String alterRental(@RequestBody Rental rental, @PathVariable Integer id){
        return msqRentals.alterRental(rental,id);
    }
    @DeleteMapping ("/rentals/{id}")
    public String deleteRental(@PathVariable Integer id){
        return msqRentals.deleteRental(id);
    }




}
