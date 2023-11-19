package com.bda.recu.services.mappers;

import com.bda.recu.dtos.InvoiceItemDTO;
import com.bda.recu.models.InvoiceItem;
import com.bda.recu.services.InvoiceService;
import com.bda.recu.services.TrackService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class InvoiceItemMapper implements Function<InvoiceItemDTO, InvoiceItem> {

    private final InvoiceService invoiceService;
    private final TrackService trackService;

    public InvoiceItemMapper(InvoiceService invoiceService, TrackService trackService) {
        this.invoiceService = invoiceService;
        this.trackService = trackService;
    }

    @Override
    public InvoiceItem apply(InvoiceItemDTO invoiceItemDTO) {
        return new InvoiceItem(
                invoiceItemDTO.getInvoiceLineId(),
                invoiceService.map(invoiceService.getById(invoiceItemDTO.getInvoiceId())),
                trackService.map(trackService.getById(invoiceItemDTO.getTrackId())),
                invoiceItemDTO.getUnitPrice(),
                invoiceItemDTO.getQuantity()
        );
    }
}
