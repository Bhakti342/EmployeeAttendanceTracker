package com.example.project.employee_attendance_tracker.controller;

import com.example.project.employee_attendance_tracker.dto.LoginDto;
import com.example.project.employee_attendance_tracker.models.Employee;
import com.example.project.employee_attendance_tracker.service.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServices userServices;

    @PostMapping("/register")
    public String registerUser(@RequestBody Employee employee){
        return userServices.registerUser(employee);
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDto loginInfo){
        return userServices.loginUser(loginInfo);
    }
}
