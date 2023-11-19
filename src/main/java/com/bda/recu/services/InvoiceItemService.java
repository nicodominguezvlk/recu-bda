package com.bda.recu.services;

import com.bda.recu.dtos.InvoiceItemDTO;
import com.bda.recu.models.InvoiceItem;
import com.bda.recu.repos.InvoiceItemRepository;
import com.bda.recu.services.mappers.InvoiceItemDTOMapper;
import com.bda.recu.services.mappers.InvoiceItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceItemMapper entityMapper;
    private final InvoiceItemDTOMapper DTOmapper;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository, InvoiceItemMapper entityMapper, InvoiceItemDTOMapper DTOmapper) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public InvoiceItemDTO add(InvoiceItemDTO entityDTO){
        Optional<InvoiceItem> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(invoiceItemRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public InvoiceItemDTO update(InvoiceItemDTO dto){
        Optional<InvoiceItem> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(invoiceItemRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public InvoiceItemDTO delete(int id){
        InvoiceItemDTO dto = getById(id);
        if(dto != null){
            Optional<InvoiceItem> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(invoiceItemRepository::delete);
        }
        return dto;
    }

    public InvoiceItemDTO getById(int id){
        Optional<InvoiceItem> entity = invoiceItemRepository.findById(id);
        return entity.map(DTOmapper).orElseThrow();
    }

    public List<InvoiceItemDTO> getAll(){
        List<InvoiceItem> entities = invoiceItemRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return invoiceItemRepository.existsById(id); }

    public InvoiceItem map(InvoiceItemDTO dto){
        return entityMapper.apply(dto);
    }

    public InvoiceItemDTO map(InvoiceItem entity){
        return DTOmapper.apply(entity);
    }
}
