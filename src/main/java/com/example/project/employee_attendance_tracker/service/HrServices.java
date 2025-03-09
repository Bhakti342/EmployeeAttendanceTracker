package com.example.project.employee_attendance_tracker.service;

import com.example.project.employee_attendance_tracker.dto.AttendanceReqDto;
import com.example.project.employee_attendance_tracker.models.Attendance;
import com.example.project.employee_attendance_tracker.repository.AttendanceRepo;
import com.example.project.employee_attendance_tracker.repository.EmpRepo;
import com.example.project.employee_attendance_tracker.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class HrServices {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmpRepo empolyeeRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Cacheable(value = "allEmployeeData", key = "'allEmployees'")
    public List<Employee> getAllEmp() {
        try {
            return empolyeeRepo.findActiveEmployees();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Cacheable(value = "employeeById", key = "#id")
    public Employee getById(Long id) {
        Employee employee = empolyeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return employee;
    }

    @CacheEvict(value = "allEmployeeData", key = "'allEmployees'")
    public String addEmployee(Employee employee) {
        try {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            empolyeeRepo.save(employee);
            return "Employee added successfully";
        } catch (Exception e) {
            throw new RuntimeException("Failed to add employee", e);
        }
    }

    @CacheEvict(value = "allEmployeeData", key = "'allEmployees'")
    public String removeEmployee(Long id) {
        try{
            Employee employee = empolyeeRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
            employee.setActive(false);
            List<Attendance> attendanceRecords = attendanceRepo.findActiveAttendanceByEmployee(employee.getId());
            for (Attendance attendance : attendanceRecords) {
                attendance.setState(false);
            }
            empolyeeRepo.save(employee);
            attendanceRepo.saveAll(attendanceRecords);
            return "Employee removed successfully";
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to remove employee", e);
        }
    }

}
