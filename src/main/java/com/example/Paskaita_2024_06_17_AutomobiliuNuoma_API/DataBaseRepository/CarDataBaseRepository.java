package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataBaseRepository;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Car;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Client;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType.Rental;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class CarDataBaseRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    //-------------- Cars ---------------------
    public String getAllCars(){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.car";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
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
    public String getAllAvailableCars(){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.car WHERE available = 1";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
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
    public String getAllCarsByMake(String make){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.car WHERE make = ? ";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,make);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Car car = new Car();

            while (resultSet.next()) {

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
    public String getAllCarsByYear(String shearchedYear) {
        String sql = "SELECT * FROM automobiuliu_nuomos_data.car WHERE year = ? ";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, shearchedYear);
            ResultSet resultSet = preparedStatement.executeQuery();

            Car car = new Car();

            while (resultSet.next()) {

                LocalDate year = formatDate(resultSet.getString("year"));

                car.setId(resultSet.getInt("id"));
                car.setMake(resultSet.getString("make"));
                car.setModel(resultSet.getString("model"));
                car.setYear(year);
                car.setAvailability(resultSet.getBoolean("available"));

                stringbuilder.append(car);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringbuilder.toString();
    }
    public String getCarsRentedMoreThan(Integer count){
        String sql = "SELECT c.id, c.make, c.model, c.year, c.available, COUNT(c.id) as count FROM automobiuliu_nuomos_data.car c INNER JOIN  automobiuliu_nuomos_data.rental r ON c.id = r.car_id GROUP BY c.id HAVING count > ?;";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,count);
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
    public String getCarById(Integer id){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.car WHERE id = ? ";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Car car = new Car();
            if (!resultSet.next()) return "No car with id: " + id;

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
    public boolean checkAvailableCar(Integer id,String period){

        String sql = "SELECT return_date, rental_date  FROM automobiuliu_nuomos_data.rental WHERE car_id = ? AND ? BETWEEN rental_date AND return_date ";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,period);
            ResultSet resultSet =  preparedStatement.executeQuery();

            return !resultSet.next();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String createCar(Car car){
        String sql = "INSERT INTO automobiuliu_nuomos_data.car (make,model,year,available)\n" +
                "VALUES (?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,car.getMake());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setString(3,car.getYear().getYear()+ "");
            preparedStatement.setBoolean(4,car.isAvailability());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Car was added successfully";
    }
    public String alterCar(Car car,Integer id){

        String sql = "UPDATE automobiuliu_nuomos_data.car SET make = ?, model = ?, year = ?, available = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,car.getMake());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setString(3,car.getYear().getYear()+ "");
            preparedStatement.setBoolean(4,car.isAvailability());
            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return "Car was updated successfully";
    }
    public String deleteCar(Integer id){

        String sql = "DELETE FROM automobiuliu_nuomos_data.car WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (Exception e){
            return e.getMessage();
        }

        return "Successfully deleted";
    }
    public void deleteRentalByCarID(Integer car_id){

        String sql = "DELETE FROM automobiuliu_nuomos_data.rental WHERE car_id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,car_id);
            statement.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException();
        }

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
