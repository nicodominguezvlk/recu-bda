package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CondInvoiceDTO {
    private int customerId;
    private int artistId;
    private int maxAmount;
}
