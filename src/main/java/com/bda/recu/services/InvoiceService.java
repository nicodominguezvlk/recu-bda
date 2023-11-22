package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.dtos.InvoiceDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Invoice;
import com.bda.recu.repos.InvoiceRepository;
import com.bda.recu.services.mappers.InvoiceDTOMapper;
import com.bda.recu.services.mappers.InvoiceMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper entityMapper;
    private final InvoiceDTOMapper DTOmapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper entityMapper, InvoiceDTOMapper DTOmapper) {
        this.invoiceRepository = invoiceRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
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
}
