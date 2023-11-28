package com.example.Rnr.repository.user;

public enum UserType {
    DOCTOR(1,"patient"),
    PATIENT(2,"doctor");

    private int userTypeId;
    private String role;

    UserType(int userTypeId,String role){
        this.userTypeId = userTypeId;
        this.role = role;
    }
}
