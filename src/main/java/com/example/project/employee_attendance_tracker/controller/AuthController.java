package com.example.project.employee_attendance_tracker.controller;

import com.example.project.employee_attendance_tracker.JwtUtil;
import com.example.project.employee_attendance_tracker.dto.LoginDto;
import com.example.project.employee_attendance_tracker.models.AuthenticationRequest;
import com.example.project.employee_attendance_tracker.models.AuthenticationResponse;
import com.example.project.employee_attendance_tracker.models.Employee;
import com.example.project.employee_attendance_tracker.service.AuthServices;
import com.example.project.employee_attendance_tracker.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServices userServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody Employee employee){
        return userServices.registerUser(employee);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) throws Exception {
        return userServices.createAuthenticationToken(loginDto);
    }
//    @PostMapping("/hell")
//    public String hello(){
//        return "Hello World";
//    }
//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken() throws Exception{
//        try {
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch(BadCredentialsException e){
//            throw new Exception("Incorrect username or password", e);
//        }
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//
//        final String jwt = jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
}
