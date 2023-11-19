package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "invoice_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceLineId")
    private int invoiceLineId;

    @ManyToOne
    @JoinColumn(name = "InvoiceId")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "TrackId")
    private Track track;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "quantity")
    private int quantity;
}
