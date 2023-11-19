package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.models.Album;
import com.bda.recu.repos.AlbumRepository;
import com.bda.recu.services.mappers.AlbumDTOMapper;
import com.bda.recu.services.mappers.AlbumMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper entityMapper;
    private final AlbumDTOMapper DTOmapper;

    public AlbumService(AlbumRepository albumRepository, AlbumMapper entityMapper, AlbumDTOMapper DTOmapper) {
        this.albumRepository = albumRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public AlbumDTO add(AlbumDTO entityDTO){
        Optional<Album> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(albumRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public AlbumDTO update(AlbumDTO dto){
        Optional<Album> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(albumRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public AlbumDTO delete(int id){
        AlbumDTO dto = getById(id);
        if(dto != null){
            Optional<Album> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(albumRepository::delete);
        }
        return dto;
    }

    public AlbumDTO getById(int id){
        Optional<Album> entity = albumRepository.findById(id);
        return entity.map(DTOmapper).orElseThrow();
    }

    public List<AlbumDTO> getAll(){
        List<Album> entities = albumRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return albumRepository.existsById(id); }

    public Album map(AlbumDTO dto){
        return entityMapper.apply(dto);
    }

    public AlbumDTO map(Album entity){
        return DTOmapper.apply(entity);
    }
}
