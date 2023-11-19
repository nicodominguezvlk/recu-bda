package com.bda.recu.services.mappers;

import com.bda.recu.dtos.EmployeeDTO;
import com.bda.recu.models.Employee;
import com.bda.recu.repos.EmployeeRepository;
import com.bda.recu.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class EmployeeMapper implements Function<EmployeeDTO, Employee> {

    private final EmployeeRepository employeeRepository;

    public EmployeeMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee apply(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getLastName(),
                employeeDTO.getFirstName(),
                employeeDTO.getTitle(),
                employeeRepository.findById(employeeDTO.getReportsTo()).orElseThrow(),
                employeeDTO.getBirthDate(),
                employeeDTO.getHireDate(),
                employeeDTO.getAddress(),
                employeeDTO.getCity(),
                employeeDTO.getState(),
                employeeDTO.getCountry(),
                employeeDTO.getPostalCode(),
                employeeDTO.getPhone(),
                employeeDTO.getFax(),
                employeeDTO.getEmail(),
                new ArrayList<>()
        );
    }
}
