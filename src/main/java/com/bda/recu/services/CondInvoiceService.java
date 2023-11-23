package com.bda.recu.services;

import com.bda.recu.dtos.*;
import com.bda.recu.models.Invoice;
import com.bda.recu.models.InvoiceItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CondInvoiceService {

    private final CustomerService customerService;
    private final InvoiceService invoiceService;
    private final InvoiceItemService invoiceItemService;
    private final TrackService trackService;
    private final ArtistService artistService;
    private final MediaTypeService mediaTypeService;

    public CondInvoiceService(CustomerService customerService, InvoiceService invoiceService, InvoiceItemService invoiceItemService, TrackService trackService, ArtistService artistService, MediaTypeService mediaTypeService) {
        this.customerService = customerService;
        this.invoiceService = invoiceService;
        this.invoiceItemService = invoiceItemService;
        this.trackService = trackService;
        this.artistService = artistService;
        this.mediaTypeService = mediaTypeService;
    }

    public OutCondInvoiceDTO addCondInvoice(InCondInvoiceDTO condInvoiceDTO){

        // Check artist and media type
        if (!artistService.existsById(condInvoiceDTO.getArtistId()) || !mediaTypeService.existsById(condInvoiceDTO.getMediaTypeId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Get customer
        CustomerDTO customerDTO = customerService.getById(condInvoiceDTO.getCustomerId());

        // Create invoice
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setCustomerId(customerDTO.getCustomerId());
        invoiceDTO.setInvoiceDate(LocalDateTime.now());
        invoiceDTO.setBillingAddress(customerDTO.getAddress());
        invoiceDTO.setBillingCity(customerDTO.getCity());
        invoiceDTO.setBillingState(customerDTO.getState());
        invoiceDTO.setBillingCountry(customerDTO.getCountry());
        invoiceDTO.setBillingPostalCode(customerDTO.getPostalCode());
        invoiceDTO.setTotal(0);

        invoiceDTO = invoiceService.add(invoiceDTO);

        // Get invoices from customer
        List<Invoice> customerInvoices = invoiceService.getAllRaw().stream()
                .filter(invoice -> invoice.getCustomer().getCustomerId() == condInvoiceDTO.getCustomerId())
                .toList();

        // Get invoice items from customer
        List<InvoiceItem> customerInvoiceItems = new ArrayList<>();
        for (Invoice customerInvoice : customerInvoices){
            customerInvoiceItems.addAll(customerInvoice.getInvoiceItemList());
        }

        // Get tracks from customer
        List<TrackDTO> customerTracks = new ArrayList<>();
        for (InvoiceItem customerInvoiceItem : customerInvoiceItems){
            customerTracks.add(trackService.map(customerInvoiceItem.getTrack()));
        }

        // Get available tracks for customer
        List<TrackDTO> allTracks = trackService.getAll();
        List<TrackDTO> avTracks = new ArrayList<>();
        for (TrackDTO tr : allTracks){
            if (!customerTracks.contains(tr)){
                avTracks.add(tr);
            }
        }

        // Artist filtering
        avTracks = avTracks.stream()
                .filter(t -> trackService.map(t).getAlbum() != null)
                .filter(t -> trackService.map(t).getAlbum().getArtist().getArtistId() == condInvoiceDTO.getArtistId())
                .toList();

        // Media type filtering
        avTracks = avTracks.stream()
                .filter(t -> trackService.map(t).getMediaType().getMediaTypeId() == condInvoiceDTO.getMediaTypeId())
                .toList();

        if (avTracks.isEmpty()){
            invoiceService.delete(invoiceDTO.getInvoiceId());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        // At least one track per album
        List<TrackDTO> filtTracks = new ArrayList<>();
        List<Integer> albumIds = new ArrayList<>();
        for (TrackDTO avTrack: avTracks) {
            if (!albumIds.contains(avTrack.getAlbumId())){
                albumIds.add(avTrack.getAlbumId());
                filtTracks.add(avTrack);
            }
        }

        // Limit total
        double tot = 0;
        int i = 0;
        List<TrackDTO> buyTracks = new ArrayList<>();
        while (i < filtTracks.size()){ // One per album
            tot = tot + filtTracks.get(i).getUnitPrice();
            if (condInvoiceDTO.getMaxAmount() - tot >= 0){
                buyTracks.add(filtTracks.get(i));
            }
            else{
                tot = tot - avTracks.get(i).getUnitPrice();
                break;
            }
            i += 1;
        }
        int j = 0;
        while (j < avTracks.size()){ // Fill with the rest of tracks
            tot = tot + avTracks.get(j).getUnitPrice();
            if (condInvoiceDTO.getMaxAmount() - tot >= 0){
                if (!buyTracks.contains(avTracks.get(i))){
                    buyTracks.add(avTracks.get(i));
                }
                else {
                    tot = tot - avTracks.get(j).getUnitPrice();
                }
            }
            else{
                tot = tot - avTracks.get(j).getUnitPrice();
                break;
            }
            j += 1;
        }

        // Create invoice items
        for (TrackDTO buyTrack : buyTracks){
            InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO();
            invoiceItemDTO.setInvoiceId(invoiceDTO.getInvoiceId());
            invoiceItemDTO.setTrackId(buyTrack.getTrackId());
            invoiceItemDTO.setUnitPrice(buyTrack.getUnitPrice());
            invoiceItemDTO.setQuantity(1);

            invoiceItemService.add(invoiceItemDTO);
        }

        // Update invoice total
        invoiceDTO.setTotal(tot);

        // Sort tracks by albumId
        buyTracks.sort(Comparator.comparingInt(TrackDTO::getAlbumId));

        // Get total seconds
        int seconds = 0;
        for (TrackDTO track: buyTracks) {
            seconds += track.getMilliseconds() / 1000;
        }

        // Create out DTO structure

        return new OutCondInvoiceDTO(
                customerDTO,
                tot,
                seconds,
                buyTracks
        );
    }
}
