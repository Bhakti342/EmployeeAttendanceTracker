package com.example.project.employee_attendance_tracker.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
