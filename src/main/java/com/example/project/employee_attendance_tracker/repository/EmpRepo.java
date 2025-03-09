package com.example.project.employee_attendance_tracker.repository;

import com.example.project.employee_attendance_tracker.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.active = true")
    List<Employee> findActiveEmployees();

    @Query("SELECT e.id FROM Employee e WHERE e.active = true")
    List<Long> findAllActiveEmployeeIds();

    Optional<Employee> findByUsername(String username);
}
