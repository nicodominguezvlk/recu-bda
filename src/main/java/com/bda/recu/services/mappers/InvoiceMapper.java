package com.bda.recu.services.mappers;

import com.bda.recu.dtos.InvoiceDTO;
import com.bda.recu.models.Invoice;
import com.bda.recu.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class InvoiceMapper implements Function<InvoiceDTO, Invoice> {

    private final CustomerService customerService;

    public InvoiceMapper(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Invoice apply(InvoiceDTO invoiceDTO) {
        return new Invoice(
                invoiceDTO.getInvoiceId(),
                customerService.map(customerService.getById(invoiceDTO.getCustomerId())),
                invoiceDTO.getInvoiceDate(),
                invoiceDTO.getBillingAddress(),
                invoiceDTO.getBillingCity(),
                invoiceDTO.getBillingState(),
                invoiceDTO.getBillingCountry(),
                invoiceDTO.getBillingPostalCode(),
                invoiceDTO.getTotal(),
                new ArrayList<>()
        );
    }
}
