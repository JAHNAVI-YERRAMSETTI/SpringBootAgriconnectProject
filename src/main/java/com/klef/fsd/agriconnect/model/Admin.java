package com.klef.fsd.agriconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_table")
public class Admin {

    @Id
    @Column(length = 50)
    private String username;
    
    @Column(length = 50, nullable = false)
    private String password;
    
    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 15)
    private String phone;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Optional: If needed, you can implement an ID method here
    public String getId() {
        return username;
    }
}







