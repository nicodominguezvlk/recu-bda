package com.bda.recu.services.mappers;

import com.bda.recu.dtos.InvoiceItemDTO;
import com.bda.recu.models.InvoiceItem;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class InvoiceItemDTOMapper implements Function<InvoiceItem, InvoiceItemDTO> {

    @Override
    public InvoiceItemDTO apply(InvoiceItem invoiceItem) {
        return new InvoiceItemDTO(
                invoiceItem.getInvoiceLineId(),
                invoiceItem.getInvoice().getInvoiceId(),
                invoiceItem.getTrack().getTrackId(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getQuantity()
        );
    }
}
