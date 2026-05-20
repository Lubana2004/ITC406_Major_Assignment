package com.example.itc406_major_assignment;

public class UserModel {

    int id;
    String firstname;
    String lastname;
    String role;

    public UserModel(int id,
                     String firstname,
                     String lastname,
                     String role) {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }
}