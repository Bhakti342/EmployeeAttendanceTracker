package com.example.project.employee_attendance_tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class EmployeeAttendanceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAttendanceTrackerApplication.class, args);
	}



}
