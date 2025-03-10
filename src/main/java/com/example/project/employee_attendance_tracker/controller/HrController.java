package com.example.project.employee_attendance_tracker.controller;

import com.example.project.employee_attendance_tracker.dto.AddAttendanceDto;
import com.example.project.employee_attendance_tracker.dto.AttendanceReqDto;
import com.example.project.employee_attendance_tracker.dto.RemoveAttendanceDto;
import com.example.project.employee_attendance_tracker.models.Attendance;
import com.example.project.employee_attendance_tracker.models.Employee;
import com.example.project.employee_attendance_tracker.service.AttendanceServices;
import com.example.project.employee_attendance_tracker.service.HrServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/hr")
public class HrController {

    @Autowired
    HrServices hrServices;

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

    @GetMapping("/allEmp")
    public List<Employee> getAllEmployee(){
        return hrServices.getAllEmp();
    }

    @GetMapping("/empId/{id}")
    public Employee getById(@PathVariable Long id){
        return hrServices.getById(id);
    }

    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee){
        return hrServices.addEmployee(employee);
    }

    @PostMapping("/ownRecords")
    public List<Attendance> getAttendanceRecords(@RequestBody AttendanceReqDto attendanceReqDto){
        return attendanceServices.getAttendanceRecords(attendanceReqDto);
    }

    //view Records
    @PostMapping("/records")
    public List<Attendance> getAttendanceRecordsById(@RequestBody AttendanceReqDto attendanceReqDto){
        return attendanceServices.getAttendanceRecordsById(attendanceReqDto);
    }

    //modify the attendance
    @PutMapping("/modify")
    public String addAttendanceById(@Valid @RequestBody AddAttendanceDto attendanceInfo){
        return attendanceServices.modifyAttendance(attendanceInfo);
    }

    @PatchMapping("/remove/{id}")
    public String removeEmployee(@PathVariable Long id){
        return hrServices.removeEmployee(id);
    }


    @PatchMapping("/removeRecords")
    public String removeRecordsById(@RequestBody RemoveAttendanceDto attendanceInfo){
        return attendanceServices.removeRecordsById(attendanceInfo);
    }

    @PostMapping("/downloadCsv")
    public void exportAttendanceToCSV(@RequestBody AttendanceReqDto recordsInfo, HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Attendance-Report.csv\"");

            attendanceServices.exportAttendanceToCSV(recordsInfo, response);
        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV: " + e.getMessage(), e);
        }
    }
}
