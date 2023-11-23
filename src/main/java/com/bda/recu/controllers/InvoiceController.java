package com.bda.recu.controllers;

import com.bda.recu.dtos.InCondInvoiceDTO;
import com.bda.recu.dtos.InvoiceDTO;
import com.bda.recu.dtos.OutCondInvoiceDTO;
import com.bda.recu.services.CondInvoiceService;
import com.bda.recu.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CondInvoiceService condInvoiceService;

    public InvoiceController(InvoiceService invoiceService, CondInvoiceService condInvoiceService) {
        this.invoiceService = invoiceService;
        this.condInvoiceService = condInvoiceService;
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

    @PostMapping("/autobuy")
    public ResponseEntity<OutCondInvoiceDTO> addCondInvoice(@RequestBody InCondInvoiceDTO condInvoiceDTO){
        OutCondInvoiceDTO createdDTO = condInvoiceService.addCondInvoice(condInvoiceDTO);
        // Se debe usar la version 3.41 del JDBC de SQLite (versiones posteriores no implementan el retorno del id despues de agregar una fila)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }
}
