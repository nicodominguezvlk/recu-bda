package com.bda.recu.services;

import com.bda.recu.dtos.GenreDTO;
import com.bda.recu.models.Genre;
import com.bda.recu.repos.GenreRepository;
import com.bda.recu.services.mappers.GenreDTOMapper;
import com.bda.recu.services.mappers.GenreMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper entityMapper;
    private final GenreDTOMapper DTOmapper;

    public GenreService(GenreRepository genreRepository, GenreMapper entityMapper, GenreDTOMapper DTOmapper) {
        this.genreRepository = genreRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
    }

    public GenreDTO add(GenreDTO entityDTO){
        Optional<Genre> entity = Stream.of(entityDTO).map(entityMapper).findFirst();
        entity.ifPresent(genreRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public GenreDTO update(GenreDTO dto){
        Optional<Genre> entity = Stream.of(dto).map(entityMapper).findFirst();
        entity.ifPresent(genreRepository::save);
        return entity.map(DTOmapper).orElseThrow();
    }

    public GenreDTO delete(int id){
        GenreDTO dto = getById(id);
        if(dto != null){
            Optional<Genre> entity = Stream.of(dto).map(entityMapper).findFirst();
            entity.ifPresent(genreRepository::delete);
        }
        return dto;
    }

    public GenreDTO getById(int id){
        Optional<Genre> entity = genreRepository.findById(id);
        return entity.map(DTOmapper).orElse(null);
    }

    public List<GenreDTO> getAll(){
        List<Genre> entities = genreRepository.findAll();
        return entities.stream().map(DTOmapper).toList();
    }

    public boolean existsById(int id) { return genreRepository.existsById(id); }

    public Genre map(GenreDTO dto){
        return entityMapper.apply(dto);
    }

    public GenreDTO map(Genre entity){
        return DTOmapper.apply(entity);
    }
}
