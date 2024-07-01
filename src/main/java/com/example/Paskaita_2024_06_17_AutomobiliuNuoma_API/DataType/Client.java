package com.example.Paskaita_2024_06_17_AutomobiliuNuoma_API.DataType;

public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "ID: " + id + " Name: " + firstName + " " + lastName + " e-mail: " + email + " phone number: " + phone + " \n";
    }
}
