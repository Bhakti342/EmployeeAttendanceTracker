package com.example.project.employee_attendance_tracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String username;
    private String password;

    public LoginDto(){

    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
