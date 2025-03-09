package com.example.project.employee_attendance_tracker.controller;

import com.example.project.employee_attendance_tracker.dto.AttendanceReqDto;
import com.example.project.employee_attendance_tracker.models.Attendance;
import com.example.project.employee_attendance_tracker.service.AttendanceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    @Autowired
    AttendanceServices attendanceServices;

    @PostMapping("/checkin")
    public String checkIn(){
        return attendanceServices.checkIn();
    }

    @PostMapping("/checkout")
    public String checkOut(){
        return attendanceServices.checkOut();
    }

    @PostMapping("/records")
    public List<Attendance> getAttendanceRecords(@RequestBody AttendanceReqDto attendanceReqDto){
        return attendanceServices.getAttendanceRecords(attendanceReqDto);
    }
}
