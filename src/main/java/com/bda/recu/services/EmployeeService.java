package com.bda.recu.services;

import com.bda.recu.dtos.EmployeeDTO;
import com.bda.recu.models.Employee;
import com.bda.recu.repos.EmployeeRepository;
import com.bda.recu.services.mappers.EmployeeDTOMapper;
import com.bda.recu.services.mappers.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper entityMapper;
    private final EmployeeDTOMapper DTOmapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper entityMapper, EmployeeDTOMapper DTOmapper) {
        this.employeeRepository = employeeRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public EmployeeDTO add(EmployeeDTO entityDTO){
        Optional<Employee> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(employeeRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public EmployeeDTO update(EmployeeDTO dto){
        Optional<Employee> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(employeeRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public EmployeeDTO delete(int id){
        EmployeeDTO dto = getById(id);
        if(dto != null){
            Optional<Employee> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(employeeRepository::delete);
        }
        return dto;
    }

    public EmployeeDTO getById(int id){
        Optional<Employee> entity = employeeRepository.findById(id);
        return entity.map(DTOmapper).orElseThrow();
    }

    public List<EmployeeDTO> getAll(){
        List<Employee> entities = employeeRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return employeeRepository.existsById(id); }

    public Employee map(EmployeeDTO dto){
        return entityMapper.apply(dto);
    }

    public EmployeeDTO map(Employee entity){
        return DTOmapper.apply(entity);
    }
}
