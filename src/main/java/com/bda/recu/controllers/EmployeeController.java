package com.bda.recu.controllers;

import com.bda.recu.dtos.EmployeeDTO;
import com.bda.recu.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll(){
        List<EmployeeDTO> values = this.employeeService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> add(@RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO createdDTO = this.employeeService.add(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EmployeeDTO> delete(@PathVariable int id) {
        EmployeeDTO response = employeeService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> update(@RequestBody EmployeeDTO dto) {
        EmployeeDTO updatedDTO = employeeService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
