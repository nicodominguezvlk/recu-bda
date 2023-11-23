package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutCondInvoiceDTO {
    private CustomerDTO customer;
    private double total;
    private int seconds;
    List<TrackDTO> trackList;
}
