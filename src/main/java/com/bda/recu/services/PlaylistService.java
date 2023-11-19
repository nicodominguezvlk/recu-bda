package com.bda.recu.services;

import com.bda.recu.dtos.PlaylistDTO;
import com.bda.recu.models.Playlist;
import com.bda.recu.repos.PlaylistRepository;
import com.bda.recu.services.mappers.PlaylistDTOMapper;
import com.bda.recu.services.mappers.PlaylistMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper entityMapper;
    private final PlaylistDTOMapper DTOmapper;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMapper entityMapper, PlaylistDTOMapper DTOmapper) {
        this.playlistRepository = playlistRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public PlaylistDTO add(PlaylistDTO entityDTO){
        Optional<Playlist> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(playlistRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public PlaylistDTO update(PlaylistDTO dto){
        Optional<Playlist> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(playlistRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public PlaylistDTO delete(int id){
        PlaylistDTO dto = getById(id);
        if(dto != null){
            Optional<Playlist> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(playlistRepository::delete);
        }
        return dto;
    }

    public PlaylistDTO getById(int id){
        Optional<Playlist> entity = playlistRepository.findById(id);
        return entity.map(DTOmapper).orElseThrow();
    }

    public List<PlaylistDTO> getAll(){
        List<Playlist> entities = playlistRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return playlistRepository.existsById(id); }

    public Playlist map(PlaylistDTO dto){
        return entityMapper.apply(dto);
    }

    public PlaylistDTO map(Playlist entity){
        return DTOmapper.apply(entity);
    }
}
