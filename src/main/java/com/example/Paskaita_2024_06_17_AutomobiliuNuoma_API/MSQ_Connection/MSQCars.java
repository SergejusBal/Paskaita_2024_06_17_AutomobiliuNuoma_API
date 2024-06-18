package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Car;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Rental;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MSQCars {

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public MSQCars(String URL, String USERNAME, String PASSWORD) {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getAllCars(){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.car";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Car car = new Car();
            while (resultSet.next()){

                LocalDate year = formatDate(resultSet.getString("year"));

                car.setId(resultSet.getInt("id"));
                car.setMake(resultSet.getString("make"));
                car.setModel(resultSet.getString("model"));
                car.setYear(year);
                car.setAvailability(resultSet.getBoolean("available"));

                stringbuilder.append(car);
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
            year = LocalDate.parse("2000",dateTimeFormatter);
        }
        return year;
    }

    public String getCarById(Integer id){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.car WHERE id = ? ";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Car car = new Car();
            resultSet.next();

            LocalDate year = formatDate(resultSet.getString("year"));

            car.setId(resultSet.getInt("id"));
            car.setMake(resultSet.getString("make"));
            car.setModel(resultSet.getString("model"));
            car.setYear(year);
            car.setAvailability(resultSet.getBoolean("available"));

            stringbuilder.append(car);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringbuilder.toString();
    }

    public String createCar(Car car){

        if(
                car.getMake() == null || car.getModel() == null ||
                car.getYear() == null || car.isAvailability() == null
        )
        {
            return "Invalid Input";
        }

        String sql = "INSERT INTO automobiuliu_nuomos_data.car (make,model,year,available)\n" +
                "VALUES (?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,car.getMake());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setString(3,car.getYear().getYear()+ "");
            preparedStatement.setBoolean(4,car.isAvailability());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            return "Invalid Input: " + e.getMessage();
        }

        return "Rental was added successfully";
    }

    public String alterCar(Car car,Integer id){

        if(
                car.getMake() == null || car.getModel() == null ||
                car.getYear() == null || car.isAvailability() == null
        )
        {
            return "Invalid Input";
        }

        String sql = "UPDATE automobiuliu_nuomos_data.car SET make = ?, model = ?, year = ?, available = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,car.getMake());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setString(3,car.getYear().getYear()+ "");
            preparedStatement.setBoolean(4,car.isAvailability());
            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
        } catch (Exception e){
            return e.getMessage();
        }

        return "Rental was updated successfully";
    }


    public String deleteCar(Integer id){

        deleteRental(id);

        String sql = "DELETE FROM automobiuliu_nuomos_data.car WHERE id = ?";
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


    private void deleteRental(Integer car_id){

        String sql = "DELETE FROM automobiuliu_nuomos_data.rental WHERE car_id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,car_id);
            statement.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException();
        }

    }



}
