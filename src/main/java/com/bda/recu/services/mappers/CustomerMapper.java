package com.bda.recu.services.mappers;

import com.bda.recu.dtos.CustomerDTO;
import com.bda.recu.models.Customer;
import com.bda.recu.services.EmployeeService;
import com.bda.recu.services.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class CustomerMapper implements Function<CustomerDTO, Customer> {

    private final EmployeeService employeeService;

    public CustomerMapper(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Customer apply(CustomerDTO customerDTO) {
        return new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getCompany(),
                customerDTO.getAddress(),
                customerDTO.getCity(),
                customerDTO.getState(),
                customerDTO.getCountry(),
                customerDTO.getPostalCode(),
                customerDTO.getPhone(),
                customerDTO.getFax(),
                customerDTO.getEmail(),
                employeeService.map(employeeService.getById(customerDTO.getSupportRepId())),
                new ArrayList<>()
        );
    }
}
