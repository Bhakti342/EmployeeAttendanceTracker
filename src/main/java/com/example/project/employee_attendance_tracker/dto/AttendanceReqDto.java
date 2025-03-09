package com.example.project.employee_attendance_tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AttendanceReqDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> id;

    public AttendanceReqDto(){

    }

    public AttendanceReqDto(LocalDate startDate, LocalDate endDate, List<Long> id){
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
    }
}
