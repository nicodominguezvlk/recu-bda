package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private int employeeId;
    private String lastName;
    private String firstName;
    private String title;
    private int reportsTo;
    private LocalDateTime birthDate;
    private LocalDateTime hireDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String fax;
    private String email;
}
