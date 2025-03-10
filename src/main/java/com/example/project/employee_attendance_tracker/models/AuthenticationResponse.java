package com.example.project.employee_attendance_tracker.models;

public class AuthenticationResponse {

    private final String jwt;

    public AuthenticationResponse(String jwt){
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }
}
