package com.spring.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {


    private String empName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long empId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String department;
    private String designation;
    private Double salary;

    private LocalDate dateOfJoining;
    private LocalDate dateOfBirth;

    private Boolean active;

}
