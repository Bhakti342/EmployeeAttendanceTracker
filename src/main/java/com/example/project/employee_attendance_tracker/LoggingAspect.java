package com.example.project.employee_attendance_tracker;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterReturning("execution(* com.example.project.employee_attendance_tracker.service.AuthServices.registerUser(..))")
    public void lognewUserCreated(JoinPoint joinPoint) {
        logger.info("New User added at {}", LocalDateTime.now());
    }

    @AfterReturning("execution(* com.example.project.employee_attendance_tracker.service.AuthServices.createAuthenticationToken(..))")
    public void logLogin(JoinPoint joinPoint) throws Throwable{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String role = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.joining(", "));

            logger.info("[{}] is logged in at [{}].", role, LocalDateTime.now());
        }
    }

    @AfterReturning("execution(* com.example.project.employee_attendance_tracker.service.AttendanceServices.checkIn(..))")
    public void logCheckIn(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            logger.info("User '{}' checked in at {}", username, LocalDateTime.now());
        }
    }

    @AfterReturning("execution(* com.example.project.employee_attendance_tracker.service.AttendanceServices.checkOut(..))")
    public void logCheckOut(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            logger.info("User '{}' checked out at {}", username, LocalDateTime.now());
        }
    }

}
