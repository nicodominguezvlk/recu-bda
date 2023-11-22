package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.dtos.ArtistDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Artist;
import com.bda.recu.repos.ArtistRepository;
import com.bda.recu.services.mappers.ArtistDTOMapper;
import com.bda.recu.services.mappers.ArtistMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper entityMapper;
    private final ArtistDTOMapper DTOmapper;

    public ArtistService(ArtistRepository artistRepository, ArtistMapper entityMapper, ArtistDTOMapper DTOmapper) {
        this.artistRepository = artistRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public ArtistDTO add(ArtistDTO dto){
        Optional<Artist> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(artistRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public ArtistDTO update(ArtistDTO dto){
        Optional<Artist> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(artistRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public ArtistDTO delete(int id){
        ArtistDTO dto = getById(id);
        if(dto != null){
            Optional<Artist> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(artistRepository::delete);
        }
        return dto;
    }

    public ArtistDTO getById(int id){
        Optional<Artist> entity = artistRepository.findById(id);
        return entity.map(DTOmapper).orElse(null);
    }

    public List<ArtistDTO> getAll(){
        List<Artist> entities = artistRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return artistRepository.existsById(id); }

    public Artist map(ArtistDTO dto){
        return entityMapper.apply(dto);
    }

    public ArtistDTO map(Artist entity){
        return DTOmapper.apply(entity);
    }
}
