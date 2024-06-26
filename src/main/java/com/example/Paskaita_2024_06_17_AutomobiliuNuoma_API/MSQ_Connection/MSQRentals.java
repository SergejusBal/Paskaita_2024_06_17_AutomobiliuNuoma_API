package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection;


import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Rental;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MSQRentals {

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public MSQRentals(String URL, String USERNAME, String PASSWORD) {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getAllRentals(){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.rental";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Rental rental = new Rental();
            while (resultSet.next()){

                LocalDateTime rentalDate = formatDateTime(resultSet.getString("rental_date"));
                LocalDateTime returnDate = formatDateTime(resultSet.getString("return_date"));

                rental.setId(resultSet.getInt("id"));
                rental.setCarId(resultSet.getInt("car_id"));
                rental.setClientId(resultSet.getInt("client_id"));
                rental.setRentalDate(rentalDate);
                rental.setReturnDate(returnDate);

                stringbuilder.append(rental);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringbuilder.toString();
    }

    private LocalDateTime formatDateTime(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime rentalDate = null;
        try {
            rentalDate = LocalDateTime.parse(dateTime, dateTimeFormatter);
        }catch(DateTimeParseException | NullPointerException e) {
            rentalDate = LocalDateTime.parse("2000-01-01 00:00:00",dateTimeFormatter);
        }
        return rentalDate;

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

        String sql = "SELECT * FROM automobiuliu_nuomos_data.rental";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Rental rental = new Rental();
            while (resultSet.next()){

                LocalDateTime rentalDate = formatDateTime(resultSet.getString("rental_date"));
                LocalDateTime returnDate = formatDateTime(resultSet.getString("return_date"));

                rental.setId(resultSet.getInt("id"));
                rental.setCarId(resultSet.getInt("car_id"));
                rental.setClientId(resultSet.getInt("client_id"));
                rental.setRentalDate(rentalDate);
                rental.setReturnDate(returnDate);

                if(
                        rentalDate.plusDays(1).toLocalDate().isAfter(dateFrom) &&
                        returnDate.minusDays(1).toLocalDate().isBefore(dateTo)
                )
                    stringbuilder.append(rental);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringbuilder.toString();
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



    public String getRentalById(Integer id){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.rental WHERE id = ?";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Rental rental = new Rental();
            resultSet.next();

            LocalDateTime rentalDate = formatDateTime(resultSet.getString("rental_date"));
            LocalDateTime returnDate = formatDateTime(resultSet.getString("return_date"));

            rental.setId(resultSet.getInt("id"));
            rental.setCarId(resultSet.getInt("car_id"));
            rental.setClientId(resultSet.getInt("client_id"));
            rental.setRentalDate(rentalDate);
            rental.setReturnDate(returnDate);

            stringbuilder.append(rental);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringbuilder.toString();
    }

    public String createRental(Rental rental){

        if(
                rental.getRentalDate() == null || rental.getReturnDate() == null ||
                rental.getClientId() == 0 || rental.getCarId() == 0
        )
        {
            return "Invalid Input";
        }

        String sql = "INSERT INTO automobiuliu_nuomos_data.rental (car_id,client_id,rental_date,return_date)\n" +
                "VALUES (?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,rental.getCarId());
            preparedStatement.setInt(2,rental.getClientId());
            preparedStatement.setString(3,rental.getRentalDate().toString());
            preparedStatement.setString(4,rental.getReturnDate().toString());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            return "Invalid Input: " + e.getMessage();
        }

        return "Rental was added successfully";
    }

    public String alterRental(Rental rental,Integer id){

        if(
                rental.getRentalDate() == null || rental.getReturnDate() == null ||
                rental.getClientId() == 0 || rental.getCarId() == 0
        )
        {
            return "Invalid Input";
        }

        String sql = "UPDATE automobiuliu_nuomos_data.rental SET car_id = ?, client_id = ?, rental_date = ?, return_date = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,rental.getCarId());
            statement.setInt(2,rental.getClientId());
            statement.setString(3,rental.getRentalDate().toString());
            statement.setString(4,rental.getReturnDate().toString());
            statement.setInt(5,id);
            statement.executeUpdate();
        } catch (Exception e){
            return e.getMessage();
        }

        return "Rental was updated successfully";
    }


    public String deleteRental(Integer id){

        String sql = "DELETE FROM automobiuliu_nuomos_data.rental WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (Exception e){
            return e.getMessage();
        }

        return "Successfully deleted";
    }



}
