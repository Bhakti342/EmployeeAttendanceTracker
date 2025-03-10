//package com.example.project.employee_attendance_tracker.service;
//
//import com.example.project.employee_attendance_tracker.models.Employee;
//import com.example.project.employee_attendance_tracker.models.MyUserDetails;
//import com.example.project.employee_attendance_tracker.repository.EmpRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class EmployeeDetailService implements UserDetailsService {
//
//    @Autowired
//    EmpRepo employeeRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Employee> user = employeeRepo.findByUsername(username);
//        user.orElseThrow(()-> new UsernameNotFoundException("User Not Found" + username));
//        return user.map(MyUserDetails::new).get();
//    }
//}
