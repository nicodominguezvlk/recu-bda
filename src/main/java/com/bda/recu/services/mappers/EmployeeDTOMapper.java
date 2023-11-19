package com.bda.recu.services.mappers;

import com.bda.recu.dtos.EmployeeDTO;
import com.bda.recu.models.Employee;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmployeeDTOMapper implements Function<Employee, EmployeeDTO> {


    @Override
    public EmployeeDTO apply(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getLastName(),
                employee.getFirstName(),
                employee.getTitle(),
                employee.getReportsTo().getEmployeeId(),
                employee.getBirthDate(),
                employee.getHireDate(),
                employee.getAddress(),
                employee.getCity(),
                employee.getState(),
                employee.getCountry(),
                employee.getPostalCode(),
                employee.getPhone(),
                employee.getFax(),
                employee.getEmail()
        );
    }
}
