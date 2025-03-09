package com.example.project.employee_attendance_tracker.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddAttendanceDto {

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    @NotNull(message = "Please provide id.")
    private Long id;
    private LocalDate date;

    public AddAttendanceDto(){

    }

    public AddAttendanceDto(LocalDateTime checkIn, LocalDateTime checkOut, Long id, LocalDate date) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.id = id;
        this.date = date;
    }
}
