package com.example.project.employee_attendance_tracker.service;

import com.example.project.employee_attendance_tracker.JwtUtil;
import com.example.project.employee_attendance_tracker.dto.LoginDto;
import com.example.project.employee_attendance_tracker.models.AuthenticationRequest;
import com.example.project.employee_attendance_tracker.models.AuthenticationResponse;
import com.example.project.employee_attendance_tracker.models.Employee;
import com.example.project.employee_attendance_tracker.repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Service
public class AuthServices {

    @Autowired
    private EmpRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

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

    public ResponseEntity<?> createAuthenticationToken(LoginDto loginDto) throws Exception{
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String jwt = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication);

    }

//    public String loginUser(LoginDto loginInfo){
//        try {
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            httpSession.setAttribute("user", loginInfo.getUsername());
//            return "Login Successful";
//        } catch (BadCredentialsException e) {
//            return "Invalid username or password!";
//        }
//    }

}
