package com.bda.recu.services.mappers;

import com.bda.recu.dtos.GenreDTO;
import com.bda.recu.models.Genre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class GenreMapper implements Function<GenreDTO, Genre> {

    @Override
    public Genre apply(GenreDTO genreDTO) {
        return new Genre(
                genreDTO.getGenreId(),
                genreDTO.getName(),
                new ArrayList<>()
        );
    }
}
