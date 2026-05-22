package com.example.itc406_major_assignment;

public class UserModel {

    String id;
    String firstname;
    String lastname;
    String role;

    public UserModel(String id,
                     String firstname,
                     String lastname,
                     String role) {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRole() {
        return role;
    }
}