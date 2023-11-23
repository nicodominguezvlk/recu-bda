package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InCondInvoiceDTO {
    private int customerId;
    private int artistId;
    private int mediaTypeId;
    private int maxAmount;
}
