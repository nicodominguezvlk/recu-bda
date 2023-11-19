package com.bda.recu.services;

import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.Track;
import com.bda.recu.repos.TrackRepository;
import com.bda.recu.services.mappers.TrackDTOMapper;
import com.bda.recu.services.mappers.TrackMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final TrackMapper entityMapper;
    private final TrackDTOMapper DTOmapper;

    public TrackService(TrackRepository trackRepository, TrackMapper entityMapper, TrackDTOMapper DTOmapper) {
        this.trackRepository = trackRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public TrackDTO add(TrackDTO entityDTO){
        Optional<Track> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(trackRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public TrackDTO update(TrackDTO dto){
        Optional<Track> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(trackRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public TrackDTO delete(int id){
        TrackDTO dto = getById(id);
        if(dto != null){
            Optional<Track> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(trackRepository::delete);
        }
        return dto;
    }

    public TrackDTO getById(int id){
        Optional<Track> entity = trackRepository.findById(id);
        return entity.map(DTOmapper).orElseThrow();
    }

    public List<TrackDTO> getAll(){
        List<Track> entities = trackRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return trackRepository.existsById(id); }

    public Track map(TrackDTO dto){
        return entityMapper.apply(dto);
    }

    public TrackDTO map(Track entity){
        return DTOmapper.apply(entity);
    }
}
