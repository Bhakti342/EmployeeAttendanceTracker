package com.example.project.employee_attendance_tracker.service;

import com.example.project.employee_attendance_tracker.dto.LoginDto;
import com.example.project.employee_attendance_tracker.models.Employee;
import com.example.project.employee_attendance_tracker.repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthServices {

    @Autowired
    private EmpRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpSession httpSession;

    public String registerUser(Employee employee){
        if(employeeRepo.findByUsername(employee.getUsername()).isPresent()){
            return "Employee already exists";
        }
        employee.setPassword((passwordEncoder.encode(employee.getPassword())));
        employeeRepo.save(employee);
        return "User Registered Successfully!";
    }

    public String loginUser(LoginDto loginInfo){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            httpSession.setAttribute("user", loginInfo.getUsername());
            return "Login Successful";
        } catch (BadCredentialsException e) {
            return "Invalid username or password!";
        }
    }

}
