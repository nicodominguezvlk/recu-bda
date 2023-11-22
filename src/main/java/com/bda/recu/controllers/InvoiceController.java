package com.bda.recu.controllers;

import com.bda.recu.dtos.InvoiceDTO;
import com.bda.recu.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAll(){
        List<InvoiceDTO> values = this.invoiceService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> add(@RequestBody InvoiceDTO invoiceDTO){
        InvoiceDTO createdDTO = this.invoiceService.add(invoiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InvoiceDTO> delete(@PathVariable int id) {
        InvoiceDTO response = invoiceService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<InvoiceDTO> update(@RequestBody InvoiceDTO dto) {
        InvoiceDTO updatedDTO = invoiceService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
