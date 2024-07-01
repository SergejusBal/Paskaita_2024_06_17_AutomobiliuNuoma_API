package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.RestControllers;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Rental;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection.RentalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RentalController {
    private final RentalsService rentalsService;

    @Autowired
    public RentalController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @GetMapping("/rentals")
    public String getAllRentals(){
        return rentalsService.getAllRentals();
    }
    @GetMapping("/rentals/{id}")
    public String getRentalById(@PathVariable Integer id){
        return rentalsService.getRentalById(id);
    }
    @PostMapping ("/rentals")
    public String createRental(@RequestBody Rental rental){
        return rentalsService.createRental(rental);
    }
    @PutMapping ("/rentals/{id}")
    public String alterRental(@RequestBody Rental rental, @PathVariable Integer id){
        return rentalsService.alterRental(rental,id);
    }
    @DeleteMapping ("/rentals/{id}")
    public String deleteRental(@PathVariable Integer id){
        return rentalsService.deleteRental(id);
    }
        @GetMapping("/rentals/period/{period}")
    public String getRentalById(@PathVariable String period){
        return rentalsService.getRentalsByPeriod(period);
    }

}
