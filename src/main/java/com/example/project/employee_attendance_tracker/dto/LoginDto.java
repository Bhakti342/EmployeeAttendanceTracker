package com.example.project.employee_attendance_tracker.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginDto {

    @NotNull
    private String username;
    @NotNull
    private String password;

    public LoginDto(){

    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
