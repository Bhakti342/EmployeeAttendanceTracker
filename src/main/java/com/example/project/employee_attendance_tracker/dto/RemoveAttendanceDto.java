package com.example.project.employee_attendance_tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RemoveAttendanceDto {
    private Long id;

    private LocalDate date;

    public RemoveAttendanceDto(){

    }

    public RemoveAttendanceDto(Long id, LocalDate date) {
        this.id = id;
        this.date = date;
    }
}
