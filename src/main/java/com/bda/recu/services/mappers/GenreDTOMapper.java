package com.bda.recu.services.mappers;

import com.bda.recu.dtos.GenreDTO;
import com.bda.recu.models.Genre;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GenreDTOMapper implements Function<Genre, GenreDTO> {
    @Override
    public GenreDTO apply(Genre genre) {
        return new GenreDTO(
                genre.getGenreId(),
                genre.getName()
        );
    }
}
