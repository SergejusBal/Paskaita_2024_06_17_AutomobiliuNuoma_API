package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.MSQ_Connection;

import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Car;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Client;
import com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.Data.Rental;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class MSQClients {

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public MSQClients(String URL, String USERNAME, String PASSWORD) {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getAllClients(){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.client";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Client client = new Client();
            while (resultSet.next()){


                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getString("phone"));

                stringbuilder.append(client);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);        }

        return stringbuilder.toString();
    }



    public String getClientRentals(Integer id){
        String sql = "SELECT * FROM automobiuliu_nuomos_data.rental WHERE client_id = ?";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Rental rental = new Rental();

            while (resultSet.next()){

                LocalDateTime rentalDate = formatDate(resultSet.getString("rental_date"));
                LocalDateTime returnDate = formatDate(resultSet.getString("return_date"));

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

    private LocalDateTime formatDate(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime rentalDate = null;
        try {
            rentalDate = LocalDateTime.parse(dateTime, dateTimeFormatter);
        }catch(DateTimeParseException | NullPointerException e) {
            rentalDate = LocalDateTime.parse("2000-01-01 00:00:00",dateTimeFormatter);
        }
        return rentalDate;
    }


    public String getClientsById(Integer id){

        String sql = "SELECT * FROM automobiuliu_nuomos_data.client WHERE id = ? ";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Client client = new Client();
            if (!resultSet.next()) return "No client with id: " + id;

            client.setId(resultSet.getInt("id"));
            client.setFirstName(resultSet.getString("first_name"));
            client.setLastName(resultSet.getString("last_name"));
            client.setEmail(resultSet.getString("email"));
            client.setPhone(resultSet.getString("phone"));

            stringbuilder.append(client);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringbuilder.toString();
    }

    public String createClient(Client client){

        if(
                client.getFirstName() == null || client.getLastName() == null ||
                client.getEmail() == null || client.getPhone() == null
        )
        {
            return "Invalid Input";
        }

        String sql = "INSERT INTO automobiuliu_nuomos_data.client (first_name,last_name,email,phone)\n" +
                "VALUES (?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,client.getFirstName());
            preparedStatement.setString(2,client.getLastName());
            preparedStatement.setString(3,client.getEmail());
            preparedStatement.setString(4,client.getPhone());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            return "Invalid Input: " + e.getMessage();
        }

        return "Client was successfully added ";
    }


    public String getAllActiveClients(){


        String sql = "SELECT c.id, c.first_name, c.last_name, c.email, c.phone FROM automobiuliu_nuomos_data.client c INNER JOIN  automobiuliu_nuomos_data.rental r ON c.id = r.client_id;";
        StringBuilder stringbuilder = new StringBuilder();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();

            Client client = new Client();
            while (resultSet.next()){


                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getString("phone"));

                stringbuilder.append(client);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);        }

        return stringbuilder.toString();

    }

    public String alterClient(Client client,Integer id){

        if(
                client.getFirstName() == null || client.getLastName() == null ||
                        client.getEmail() == null || client.getPhone() == null
        )
        {
            return "Invalid Input";
        }

        String sql = "UPDATE automobiuliu_nuomos_data.client SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,client.getFirstName());
            preparedStatement.setString(2,client.getLastName());
            preparedStatement.setString(3,client.getEmail());
            preparedStatement.setString(4,client.getPhone());

            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
        } catch (Exception e){
            return e.getMessage();
        }

        return "Client was successfully updated";
    }


    public String deleteClient (Integer id){

        deleteRental(id);

        String sql = "DELETE FROM automobiuliu_nuomos_data.client WHERE id = ?";
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


    private void deleteRental(Integer client_id){

        String sql = "DELETE FROM automobiuliu_nuomos_data.rental WHERE client_id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,client_id);
            statement.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException();
        }

    }
}
