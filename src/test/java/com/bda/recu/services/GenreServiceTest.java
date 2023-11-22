package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.dtos.GenreDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Genre;
import com.bda.recu.repos.GenreRepository;
import com.bda.recu.services.mappers.AlbumDTOMapper;
import com.bda.recu.services.mappers.AlbumMapper;
import com.bda.recu.services.mappers.GenreDTOMapper;
import com.bda.recu.services.mappers.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GenreServiceTest {

    private GenreRepository genreRepository;
    private GenreService genreService;

    @BeforeEach
    public void setup(){
        // Genre
        genreRepository = Mockito.mock(GenreRepository.class);
        GenreMapper genreMapper = new GenreMapper();
        GenreDTOMapper genreDTOMapper = new GenreDTOMapper();
        genreService = new GenreService(genreRepository, genreMapper, genreDTOMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        GenreDTO genreDTO = new GenreDTO(1, "Post-rock");

        // Act
        GenreDTO result = genreService.add(genreDTO);

        // Assert
        assertNotNull(result);
        assertEquals(genreDTO.getGenreId(), result.getGenreId());
        assertEquals(genreDTO.getName(), result.getName());
    }

    @Test
    public void testUpdate(){
        // Arrange
        GenreDTO genreDTO = new GenreDTO(1, "Old Genre Name");
        genreService.add(genreDTO);
        genreDTO = new GenreDTO(1, "New Genre Name");

        // Act
        GenreDTO result = genreService.update(genreDTO);

        // Assert
        assertNotNull(result);
        assertEquals(genreDTO.getGenreId(), result.getGenreId());
        assertEquals(genreDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Genre genre = new Genre(1, "Post-rock", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));

        // Act
        GenreDTO result = genreService.delete(1);
        GenreDTO genreDTO = genreService.map(genre);

        // Assert
        assertNotNull(result);
        assertEquals(genreDTO.getGenreId(), result.getGenreId());
        assertEquals(genreDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Genre genre = new Genre(1, "Post-rock", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));

        // Act
        GenreDTO result = genreService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Genre genre = new Genre(1, "Post-rock", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));

        // Act
        GenreDTO result = genreService.getById(1);
        GenreDTO genreDTO = genreService.map(genre);

        // Assert
        assertNotNull(result);
        assertEquals(genreDTO.getGenreId(), result.getGenreId());
        assertEquals(genreDTO.getName(), result.getName());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Genre genre = new Genre(1, "Post-rock", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));

        // Act
        GenreDTO result = genreService.getById(2);

        // Assert
        assertNull(result);
    }
}