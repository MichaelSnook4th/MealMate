package com.mealmate.beans;

import java.util.List;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private List<Integer> categories;

 // Constructor for new users (without userId and categories)
    public User(String firstName, String lastName, String address, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.categories = null;
        
    }
    // Constructor for existing users (with userId)
    public User(int userId, String firstname, String lastname, String address, String email, String password, List<Integer> categories) {
        this.userId = userId;
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.categories = categories;
    }

    // Getter and setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Existing getters and setters...
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
