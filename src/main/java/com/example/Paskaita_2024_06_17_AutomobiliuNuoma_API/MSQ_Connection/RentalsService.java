package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection;


import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataBaseRepository.RentalDataBaseRepository;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Rental;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataBaseRepository.ClientDataBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Repository
public class RentalsService {

    private final RentalDataBaseRepository rentalDataBaseRepository;

    @Autowired
    public RentalsService(RentalDataBaseRepository rentalDataBaseRepository) {
        this.rentalDataBaseRepository = rentalDataBaseRepository;
    }

    public String getAllRentals(){
        return rentalDataBaseRepository.getAllRentals();
    }

    public String getRentalsByPeriod(String period){
        LocalDate dateFrom;
        LocalDate dateTo;

        try {
            dateFrom = formatDate(period.substring(0,10));
            dateTo = formatDate(period.substring(14,24));
        }catch (IndexOutOfBoundsException e){
            return "Ivalid input";
        }
        if(dateFrom.getYear() == 1900 || dateTo.getYear() == 1900 ) return "Invalid input";

        return rentalDataBaseRepository.getRentalsByPeriod(dateFrom,dateTo);
    }

    public String getRentalById(Integer id){
        return rentalDataBaseRepository.getRentalById(id);
    }

    public String createRental(Rental rental){

        if(
                rental.getRentalDate() == null || rental.getReturnDate() == null ||
                rental.getClientId() == 0 || rental.getCarId() == 0
        )
        {
            return "Invalid Input";
        }

        return rentalDataBaseRepository.createRental(rental);
    }

    public String alterRental(Rental rental,Integer id){
        if(
                rental.getRentalDate() == null || rental.getReturnDate() == null ||
                rental.getClientId() == 0 || rental.getCarId() == 0
        )
        {
            return "Invalid Input";
        }
        return rentalDataBaseRepository.alterRental(rental,id);
    }

    public String deleteRental(Integer id){
        return rentalDataBaseRepository.deleteRental(id);
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
