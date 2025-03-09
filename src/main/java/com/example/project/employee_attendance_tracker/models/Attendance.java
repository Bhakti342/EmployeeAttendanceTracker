package com.example.project.employee_attendance_tracker.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Setter
@Getter
@Table(name = "attendance")
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "id")
    private Employee employee;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean state = true;

    public Attendance(){

    }

    public Attendance(Long id, Employee employee, LocalDateTime checkIn, LocalDateTime checkOut, LocalDateTime createdAt, LocalDateTime updatedAt, boolean state) {
        this.id = id;
        this.employee = employee;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.state = state;
    }
}
