package com.bda.recu.services;

import com.bda.recu.dtos.CustomerDTO;
import com.bda.recu.models.Customer;
import com.bda.recu.models.Employee;
import com.bda.recu.repos.CustomerRepository;
import com.bda.recu.repos.EmployeeRepository;
import com.bda.recu.services.CustomerService;
import com.bda.recu.services.EmployeeService;
import com.bda.recu.services.mappers.CustomerDTOMapper;
import com.bda.recu.services.mappers.CustomerMapper;
import com.bda.recu.services.mappers.EmployeeDTOMapper;
import com.bda.recu.services.mappers.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup(){
        // Employee
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeMapper employeeMapper = new EmployeeMapper(employeeRepository);
        EmployeeDTOMapper employeeDTOMapper = new EmployeeDTOMapper();
        EmployeeService employeeService = new EmployeeService(employeeRepository, employeeMapper, employeeDTOMapper);

        // Customer
        customerRepository = Mockito.mock(CustomerRepository.class);
        CustomerMapper customerMapper = new CustomerMapper(employeeService);
        CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();
        customerService = new CustomerService(customerRepository, customerMapper, customerDTOMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        CustomerDTO customerDTO = new CustomerDTO(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", 1);

        // Act
        CustomerDTO result = customerService.add(customerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
    }

    @Test
    public void testUpdate(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        CustomerDTO customerDTO = new CustomerDTO(1, "Old Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", 1);
        customerService.add(customerDTO);
        customerDTO = new CustomerDTO(1, "New Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", 1);

        // Act
        CustomerDTO result = customerService.update(customerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO result = customerService.delete(1);
        CustomerDTO customerDTO = customerService.map(customer);

        // Assert
        assertNotNull(result);
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO result = customerService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO result = customerService.getById(1);
        CustomerDTO customerDTO = customerService.map(customer);

        // Assert
        assertNotNull(result);
        assertEquals(customerDTO.getCustomerId(), result.getCustomerId());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act
        CustomerDTO result = customerService.getById(2);

        // Assert
        assertNull(result);
    }
}