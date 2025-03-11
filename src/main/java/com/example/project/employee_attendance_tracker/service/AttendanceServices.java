package com.example.project.employee_attendance_tracker.service;

import com.example.project.employee_attendance_tracker.dto.AddAttendanceDto;
import com.example.project.employee_attendance_tracker.dto.AttendanceReqDto;
import com.example.project.employee_attendance_tracker.dto.RemoveAttendanceDto;
import com.example.project.employee_attendance_tracker.models.Attendance;
import com.example.project.employee_attendance_tracker.models.Employee;
import com.example.project.employee_attendance_tracker.repository.AttendanceRepo;
import com.example.project.employee_attendance_tracker.repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AttendanceServices {

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private EmpRepo employeeRepo;

    private Employee getLoggedInEmpInfo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return employeeRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    public String checkIn() {
        Employee employee = getLoggedInEmpInfo();
        LocalDate today = LocalDate.now();
        if(!employee.isActive()){
            return "This is no longer an active employee.";
        }
        List<Attendance> existingAttendance = attendanceRepo.findByEmployeeAndStateTrueOrderByCreatedAtDesc(employee);

        if (!existingAttendance.isEmpty()) {
            Attendance attendance = existingAttendance.get(0);
            if (attendance.getCheckIn().toLocalDate().equals(today)) {
                return "You have already checked in today!";
            }
        }
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setCreatedAt(LocalDateTime.now());

        attendanceRepo.save(attendance);
        return "Checked in successfully!";
    }

    public String checkOut() {
        Employee employee = getLoggedInEmpInfo();
        LocalDate today = LocalDate.now();
        if(!employee.isActive()){
            return "This is no longer an active employee.";
        }
        List<Attendance> existingAttendance = attendanceRepo.findByEmployeeAndStateTrueOrderByCreatedAtDesc(employee);

        if (!existingAttendance.isEmpty()) {
            Attendance attendance = existingAttendance.get(0);

            if (attendance.getCheckOut() != null && attendance.getCheckOut().toLocalDate().equals(today)) {
                return "You have already checked out today!";
            }

            if (attendance.getCheckIn() != null && attendance.getCheckIn().toLocalDate().equals(today)) {
                attendance.setCheckOut(LocalDateTime.now());
                attendance.setUpdatedAt(LocalDateTime.now());
                attendanceRepo.save(attendance);
                return "Checked out successfully!";
            }
        }
        return "No check-in record found for today!";
    }

//    @Cacheable(value = "attendanceRecords", key = "#username + '_' + #attendanceRecordDto.startDate + '_' + #attendanceRecordDto.endDate")
    public List<Attendance> getAttendanceRecords(AttendanceReqDto attendanceRecordDto) {
        Employee employee = getLoggedInEmpInfo();

        LocalDate startDate = attendanceRecordDto.getStartDate();
        LocalDate endDate = attendanceRecordDto.getEndDate();

        if (startDate == null) {
            startDate = LocalDate.of(2020, 1, 1); // Default start date
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return attendanceRepo.findAttendanceBetween(employee.getId(), startDateTime, endDateTime);
    }

//   @Cacheable(value = "attendanceRecordsById", key = "#attendanceReqDto.id + '_' + #attendanceReqDto?.startDate + '_' + #attendanceReqDto?.endDate")
    public List<Attendance> getAttendanceRecordsById(AttendanceReqDto attendanceReqDto) {
        Employee employee = getLoggedInEmpInfo();

        if (!employee.getRole().contains("HR")) {
            throw new RuntimeException("You are not authorized to access this feature!");
        }

        List<Long> employeeIds = attendanceReqDto.getId();
        if (employeeIds == null || employeeIds.isEmpty()) {
            employeeIds = employeeRepo.findAllActiveEmployeeIds();
        }

        LocalDate startDate = attendanceReqDto.getStartDate();
        LocalDate endDate = attendanceReqDto.getEndDate();

        if (startDate == null) {
            startDate = LocalDate.of(2020, 1, 1); // Default start date
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return attendanceRepo.findAttendanceBetweenWithIds(employeeIds, startDateTime, endDateTime);
    }

//    @CacheEvict(value = "attendanceRecordsById", key = "#existingAttendance?.employee?.id", allEntries = true)
    public String modifyAttendance(AddAttendanceDto attendanceInfo) {
        Employee isHr = getLoggedInEmpInfo();

        if (attendanceInfo == null) {
            throw new IllegalArgumentException("Attendance information is required!");
        }

        if (attendanceInfo.getDate() == null) {
            throw new IllegalArgumentException("Attendance date cannot be null!");
        }

        if (!isHr.getRole().contains("HR")) {
            return "You are not authorized to modify attendance records!";
        }

        if (attendanceInfo.getCheckIn() == null && attendanceInfo.getCheckOut() == null) {
            return "Please provide at least one field (check-in or check-out)!";
        }


        Employee employee = employeeRepo.findById(attendanceInfo.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + attendanceInfo.getId()));

        LocalDateTime referenceDateStart = attendanceInfo.getDate().atStartOfDay();
        LocalDateTime referenceDateEnd = attendanceInfo.getDate().atTime(23, 59, 59);

        Optional<Attendance> existingAttendance = attendanceRepo.findAttendanceByEmployeeAndDate(attendanceInfo.getId(), referenceDateStart, referenceDateEnd);
        Attendance attendance;
        if (existingAttendance.isPresent()) {
            attendance = existingAttendance.get();
            if (attendanceInfo.getCheckIn() != null) {
                attendance.setCheckIn(attendanceInfo.getCheckIn());
            }
            if (attendanceInfo.getCheckOut() != null) {
                attendance.setCheckOut(attendanceInfo.getCheckOut());
            }
        } else {
            attendance = new Attendance();
            attendance.setEmployee(employee);
            if (attendanceInfo.getCheckIn() != null) {
                attendance.setCheckIn(attendanceInfo.getCheckIn());
            }
            if (attendanceInfo.getCheckOut() != null) {
                attendance.setCheckOut(attendanceInfo.getCheckOut());
            }
            attendance.setCreatedAt(LocalDateTime.now());
        }

        attendance.setUpdatedAt(LocalDateTime.now());
        attendanceRepo.save(attendance);

        return "Attendance record updated successfully!";
    }

//    @CacheEvict(value = "attendanceRecordsById", key = "#attendanceInfo?.id + '_' + #attendanceInfo?.date")
    public String removeRecordsById(RemoveAttendanceDto attendanceInfo){
        Employee employee = getLoggedInEmpInfo();
        if (!employee.getRole().contains("HR")) {
            return "You are not authorized to delete attendance records!";
        }

        Employee employeeId = employeeRepo.findById(attendanceInfo.getId())
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + attendanceInfo.getId()));

        LocalDateTime startDateTime = attendanceInfo.getDate().atStartOfDay();
        LocalDateTime endDateTime = attendanceInfo.getDate().atTime(23, 59, 59);

        List<Attendance> attendanceRecords = attendanceRepo.findAttendanceBetween(attendanceInfo.getId(), startDateTime, endDateTime);

        if (attendanceRecords.isEmpty()) {
            return "No active attendance records found for the specified date.";
        }

        attendanceRecords.forEach(attendance -> attendance.setState(false)); // Mark attendance as inactive
        attendanceRepo.saveAll(attendanceRecords);
        return "Employee related attendance records deleted successfully!";
    }

    public void exportAttendanceToCSV(AttendanceReqDto attendanceInfo, HttpServletResponse response) throws Exception {

        List<Long> employeeIds = attendanceInfo.getId();
        if (employeeIds == null || employeeIds.isEmpty()) {
            employeeIds = employeeRepo.findAllActiveEmployeeIds();
        }

        LocalDate startDate = attendanceInfo.getStartDate();
        LocalDate endDate = attendanceInfo.getEndDate();

        if (startDate == null) {
            startDate = LocalDate.of(2020, 1, 1); // Default start date
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        List<Attendance> attendanceRecords = attendanceRepo.findAttendanceBetweenWithIds(
                employeeIds,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );

        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,CHECKIN,CHECKOUT,CREATEDAT,UPDATEDAT,EMPLOYEE_ID");

            for (Attendance record : attendanceRecords) {
                writer.println(record.getId() + ","
                        + record.getCheckIn() + ","
                        + record.getCheckOut() + ","
                        + record.getCreatedAt() + ","
                        + record.getUpdatedAt() + ","
                        + record.getEmployee().getId());
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file: " + e.getMessage(), e);
        }
    }
}
