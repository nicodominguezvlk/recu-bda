package com.bda.recu.controllers;

import com.bda.recu.dtos.CustomerDTO;
import com.bda.recu.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll(){
        List<CustomerDTO> values = this.customerService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> add(@RequestBody CustomerDTO customerDTO){
        CustomerDTO createdDTO = this.customerService.add(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerDTO> delete(@PathVariable int id) {
        CustomerDTO response = customerService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO dto) {
        CustomerDTO updatedDTO = customerService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
