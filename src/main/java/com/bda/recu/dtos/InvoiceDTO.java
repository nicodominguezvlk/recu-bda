package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private int invoiceId;
    private int customerId;
    private LocalDateTime invoiceDate;
    private String billingAddress;
    private String billingCity;
    private String billingState;
    private String billingCountry;
    private String billingPostalCode;
    private double total;
}
