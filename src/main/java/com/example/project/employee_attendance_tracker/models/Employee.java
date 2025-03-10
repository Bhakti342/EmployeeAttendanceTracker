package com.example.project.employee_attendance_tracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String employment_type;

    @NotBlank
    private String role;

    private boolean active = true;

    @OneToMany
    @JsonIgnore
    private List<Attendance> attendanceList;

    public Employee(){

    }

    public Employee(Long id, String username, String password, String name, String email, String employment_type, String role, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.employment_type = employment_type;
        this.role = role;
        this.active = active;
    }
}
