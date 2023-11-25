package com.example.gestioncontactsfinal.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String firstname;
    private String lastname;
    private String password;
    private String birth;
    private String email;

    public User(String firstname, String lastname, String password, String birth, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.birth = birth;
        this.email = email;
    }
    public User(String firstname, String lastname, String birth, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.birth = birth;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }
}
