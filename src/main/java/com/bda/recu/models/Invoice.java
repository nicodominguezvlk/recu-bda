package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceId")
    private int invoiceId;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;

    @Column(name = "InvoiceDate")
    private LocalDateTime invoiceDate;

    @Column(name = "BillingAddress")
    private String billingAddress;

    @Column(name = "BillingCity")
    private String billingCity;

    @Column(name = "BillingState")
    private String billingState;

    @Column(name = "BillingCountry")
    private String billingCountry;

    @Column(name = "BillingPostalCode")
    private String billingPostalCode;

    @Column(name = "Total")
    private double total;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceId")
    private List<InvoiceItem> invoiceItemList;
}
