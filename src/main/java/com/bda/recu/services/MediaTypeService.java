package com.bda.recu.services;

import com.bda.recu.dtos.MediaTypeDTO;
import com.bda.recu.models.MediaType;
import com.bda.recu.repos.MediaTypeRepository;
import com.bda.recu.services.mappers.MediaTypeDTOMapper;
import com.bda.recu.services.mappers.MediaTypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MediaTypeService {

    private final MediaTypeRepository mediaTypeRepository;
    private final MediaTypeMapper entityMapper;
    private final MediaTypeDTOMapper DTOmapper;

    public MediaTypeService(MediaTypeRepository mediaTypeRepository, MediaTypeMapper entityMapper, MediaTypeDTOMapper DTOmapper) {
        this.mediaTypeRepository = mediaTypeRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public MediaTypeDTO add(MediaTypeDTO entityDTO){
        Optional<MediaType> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(mediaTypeRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public MediaTypeDTO update(MediaTypeDTO dto){
        Optional<MediaType> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(mediaTypeRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public MediaTypeDTO delete(int id){
        MediaTypeDTO dto = getById(id);
        if(dto != null){
            Optional<MediaType> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(mediaTypeRepository::delete);
        }
        return dto;
    }

    public MediaTypeDTO getById(int id){
        Optional<MediaType> entity = mediaTypeRepository.findById(id);
        return entity.map(DTOmapper).orElseThrow();
    }

    public List<MediaTypeDTO> getAll(){
        List<MediaType> entities = mediaTypeRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return mediaTypeRepository.existsById(id); }

    public MediaType map(MediaTypeDTO dto){
        return entityMapper.apply(dto);
    }

    public MediaTypeDTO map(MediaType entity){
        return DTOmapper.apply(entity);
    }
}
