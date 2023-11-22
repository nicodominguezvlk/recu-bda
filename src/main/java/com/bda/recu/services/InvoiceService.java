package com.bda.recu.services;

import com.bda.recu.dtos.*;
import com.bda.recu.models.Invoice;
import com.bda.recu.models.InvoiceItem;
import com.bda.recu.repos.InvoiceItemRepository;
import com.bda.recu.repos.InvoiceRepository;
import com.bda.recu.services.mappers.InvoiceDTOMapper;
import com.bda.recu.services.mappers.InvoiceItemMapper;
import com.bda.recu.services.mappers.InvoiceMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper entityMapper;
    private final InvoiceDTOMapper DTOmapper;
    private final CustomerService customerService;
    private final TrackService trackService;
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper entityMapper, InvoiceDTOMapper DTOmapper, CustomerService customerService, TrackService trackService, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
        this.customerService = customerService;
        this.trackService = trackService;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public InvoiceDTO add(InvoiceDTO entityDTO){
        Optional<Invoice> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(invoiceRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public InvoiceDTO update(InvoiceDTO dto){
        Optional<Invoice> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(invoiceRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public InvoiceDTO delete(int id){
        InvoiceDTO dto = getById(id);
        if(dto != null){
            Optional<Invoice> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(invoiceRepository::delete);
        }
        return dto;
    }

    public InvoiceDTO getById(int id){
        Optional<Invoice> entity = invoiceRepository.findById(id);
        return entity.map(DTOmapper).orElse(null);
    }

    public List<InvoiceDTO> getAll(){
        List<Invoice> entities = invoiceRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return invoiceRepository.existsById(id); }

    public Invoice map(InvoiceDTO dto){
        return entityMapper.apply(dto);
    }

    public InvoiceDTO map(Invoice entity){
        return DTOmapper.apply(entity);
    }

    public InvoiceDTO addCondInvoice(CondInvoiceDTO condInvoiceDTO){

        // Get customer
        CustomerDTO customerDTO = customerService.getById(condInvoiceDTO.getCustomerId());

        // Create invoice
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                500,
                customerDTO.getCustomerId(),
                LocalDateTime.now(),
                customerDTO.getAddress(),
                customerDTO.getCity(),
                customerDTO.getState(),
                customerDTO.getCountry(),
                customerDTO.getPostalCode(),
                0
        );

        // Get invoices from customer
        List<Invoice> customerInvoices = invoiceRepository.findAll().stream()
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
        List<TrackDTO> avTracks = trackService.getAll();
        avTracks.removeAll(customerTracks);

        // Album filtering
        avTracks = avTracks.stream()
                .filter(t -> trackService.map(t).getAlbum() != null)
                .toList();

        // Artist filtering
        avTracks = avTracks.stream()
                .filter(t -> trackService.map(t).getAlbum().getArtist().getArtistId() == condInvoiceDTO.getArtistId())
                        .toList();

        // Order by album
        avTracks.sort(Comparator.comparingInt(t -> t.getAlbumId()));

        // Limit total
        double tot = 0;
        int i = 0;
        List<TrackDTO> buyTracks = new ArrayList<>();
        while (i < avTracks.size()){
            tot = tot + avTracks.get(i).getUnitPrice();
            if (condInvoiceDTO.getMaxAmount() - tot >= 0){
                buyTracks.add(avTracks.get(i));
            }
            else{
                break;
            }
            i += i;
        }

        // Create invoice items
        for (TrackDTO buyTrack : buyTracks){
            int j = 2250;
            InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(
                    j,
                    500,
                    buyTrack.getTrackId(),
                    buyTrack.getUnitPrice(),
                    1
            );
            j++;
            InvoiceItem invoiceItem = new InvoiceItem(
                    invoiceItemDTO.getInvoiceLineId(),
                    map(getById(invoiceItemDTO.getInvoiceId())),
                    trackService.map(trackService.getById(invoiceItemDTO.getTrackId())),
                    invoiceItemDTO.getUnitPrice(),
                    invoiceItemDTO.getQuantity()
            );
            invoiceItemRepository.save(invoiceItem);
        }

        // Update invoice total
        invoiceDTO.setTotal(tot);

        return invoiceDTO;
    }
}
