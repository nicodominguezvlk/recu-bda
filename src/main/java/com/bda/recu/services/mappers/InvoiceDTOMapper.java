package com.bda.recu.services.mappers;

import com.bda.recu.dtos.InvoiceDTO;
import com.bda.recu.models.Invoice;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class InvoiceDTOMapper implements Function<Invoice, InvoiceDTO> {
    @Override
    public InvoiceDTO apply(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getInvoiceId(),
                invoice.getCustomer().getCustomerId(),
                invoice.getInvoiceDate(),
                invoice.getBillingAddress(),
                invoice.getBillingCity(),
                invoice.getBillingState(),
                invoice.getBillingCountry(),
                invoice.getBillingPostalCode(),
                invoice.getTotal()
        );
    }
}
