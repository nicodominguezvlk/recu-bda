package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemDTO {
    private int invoiceLineId;
    private int invoiceId;
    private int trackId;
    private double unitPrice;
    private int quantity;
}
