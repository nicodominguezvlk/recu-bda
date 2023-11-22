package com.bda.recu.services;

import com.bda.recu.dtos.CustomerDTO;
import com.bda.recu.models.Customer;
import com.bda.recu.repos.CustomerRepository;
import com.bda.recu.services.mappers.CustomerDTOMapper;
import com.bda.recu.services.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper entityMapper;
    private final CustomerDTOMapper DTOmapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper entityMapper, CustomerDTOMapper DTOmapper) {
        this.customerRepository = customerRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public CustomerDTO add(CustomerDTO entityDTO){
        Optional<Customer> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(customerRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public CustomerDTO update(CustomerDTO dto){
        Optional<Customer> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(customerRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public CustomerDTO delete(int id){
        CustomerDTO dto = getById(id);
        if(dto != null){
            Optional<Customer> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(customerRepository::delete);
        }
        return dto;
    }

    public CustomerDTO getById(int id){
        Optional<Customer> entity = customerRepository.findById(id);
        return entity.map(DTOmapper).orElse(null);
    }

    public List<CustomerDTO> getAll(){
        List<Customer> entities = customerRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return customerRepository.existsById(id); }

    public Customer map(CustomerDTO dto){
        return entityMapper.apply(dto);
    }

    public CustomerDTO map(Customer entity){
        return DTOmapper.apply(entity);
    }
}
