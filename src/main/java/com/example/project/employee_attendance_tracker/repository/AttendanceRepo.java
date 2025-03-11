package com.example.project.employee_attendance_tracker.repository;

import com.example.project.employee_attendance_tracker.models.Attendance;
import com.example.project.employee_attendance_tracker.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.employee.active = true AND a.state = true AND a.employee.active = true")
    List<Attendance> findActiveAttendanceByEmployee(@Param("employeeId") Long employeeId);

    List<Attendance> findByEmployeeAndStateTrueOrderByCreatedAtDesc(Employee employee);


    //for employee
    @Query("SELECT a FROM Attendance a " +
            "LEFT JOIN a.employee e " +
            "WHERE e.id = :employeeId " +
            "AND a.employee.active = true AND a.state = true " +
            "AND a.checkIn BETWEEN :startDateTime AND :endDateTime")
    List<Attendance> findAttendanceBetween(@Param("employeeId") Long employeeId,
                                           @Param("startDateTime") LocalDateTime startDateTime,
                                           @Param("endDateTime") LocalDateTime endDateTime);
    //for hr
    @Query("SELECT a FROM Attendance a WHERE a.employee.id IN (:employeeIds) " +
            "AND a.state = true " +
            "AND a.checkIn BETWEEN :startDateTime AND :endDateTime")
    List<Attendance> findAttendanceBetweenWithIds(@Param("employeeIds") List<Long> employeeIds,
                                           @Param("startDateTime") LocalDateTime startDateTime,
                                           @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT a FROM Attendance a " +
            "LEFT JOIN a.employee e " +
            "WHERE e.id = :empId " +
            "AND a.employee.active = true AND a.state = true " +
            "AND a.checkIn BETWEEN :startOfDay AND :endOfDay")
    Optional<Attendance> findAttendanceByEmployeeAndDate(
            @Param("empId") Long empId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


}
