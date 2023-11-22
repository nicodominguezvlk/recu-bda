package com.bda.recu.controllers;

import com.bda.recu.dtos.InvoiceItemDTO;
import com.bda.recu.services.InvoiceItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoiceItems")
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceItemDTO>> getAll(){
        List<InvoiceItemDTO> values = this.invoiceItemService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<InvoiceItemDTO> add(@RequestBody InvoiceItemDTO invoiceItemDTO){
        InvoiceItemDTO createdDTO = this.invoiceItemService.add(invoiceItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InvoiceItemDTO> delete(@PathVariable int id) {
        InvoiceItemDTO response = invoiceItemService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<InvoiceItemDTO> update(@RequestBody InvoiceItemDTO dto) {
        InvoiceItemDTO updatedDTO = invoiceItemService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
