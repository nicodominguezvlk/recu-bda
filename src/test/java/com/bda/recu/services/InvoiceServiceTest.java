package com.bda.recu.services;

import com.bda.recu.dtos.InvoiceDTO;
import com.bda.recu.models.Employee;
import com.bda.recu.models.Invoice;
import com.bda.recu.models.Customer;
import com.bda.recu.repos.EmployeeRepository;
import com.bda.recu.repos.InvoiceRepository;
import com.bda.recu.repos.CustomerRepository;
import com.bda.recu.services.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTest {

    private InvoiceService invoiceService;
    private InvoiceRepository invoiceRepository;
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
        CustomerService customerService = new CustomerService(customerRepository, customerMapper, customerDTOMapper);

        // Invoice
        invoiceRepository = Mockito.mock(InvoiceRepository.class);
        InvoiceMapper invoiceMapper = new InvoiceMapper(customerService);
        InvoiceDTOMapper invoiceDTOMapper = new InvoiceDTOMapper();
        invoiceService = new InvoiceService(invoiceRepository, invoiceMapper, invoiceDTOMapper, customerService, trackService, invoiceItemService, invoiceItemRepository, invoiceItemMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        InvoiceDTO invoiceDTO = new InvoiceDTO(1, 1, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00);

        // Act
        InvoiceDTO result = invoiceService.add(invoiceDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceDTO.getCustomerId(), result.getCustomerId());
        assertEquals(invoiceDTO.getBillingAddress(), result.getBillingAddress());
    }

    @Test
    public void testUpdate(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        InvoiceDTO invoiceDTO = new InvoiceDTO(1, 1, LocalDateTime.now(), "Old Address", "Los Santos", "San Andreas", "US", "75000", 100.00);
        invoiceService.add(invoiceDTO);
        invoiceDTO = new InvoiceDTO(1, 1, LocalDateTime.now(), "New Address", "Los Santos", "San Andreas", "US", "75000", 100.00);

        // Act
        InvoiceDTO result = invoiceService.update(invoiceDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceDTO.getCustomerId(), result.getCustomerId());
        assertEquals(invoiceDTO.getBillingAddress(), result.getBillingAddress());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));

        // Act
        InvoiceDTO result = invoiceService.delete(1);
        InvoiceDTO invoiceDTO = invoiceService.map(invoice);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceDTO.getCustomerId(), result.getCustomerId());
        assertEquals(invoiceDTO.getBillingAddress(), result.getBillingAddress());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));

        // Act
        InvoiceDTO result = invoiceService.delete(2);

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
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));

        // Act
        InvoiceDTO result = invoiceService.getById(1);
        InvoiceDTO invoiceDTO = invoiceService.map(invoice);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceDTO.getCustomerId(), result.getCustomerId());
        assertEquals(invoiceDTO.getBillingAddress(), result.getBillingAddress());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));

        // Act
        InvoiceDTO result = invoiceService.getById(2);

        // Assert
        assertNull(result);
    }
}