package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.dtos.ArtistDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Artist;
import com.bda.recu.repos.ArtistRepository;
import com.bda.recu.services.mappers.AlbumDTOMapper;
import com.bda.recu.services.mappers.AlbumMapper;
import com.bda.recu.services.mappers.ArtistDTOMapper;
import com.bda.recu.services.mappers.ArtistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArtistServiceTest {

    private ArtistRepository artistRepository;
    private ArtistService artistService;

    @BeforeEach
    public void setup(){
        // Artist
        artistRepository = Mockito.mock(ArtistRepository.class);
        ArtistMapper artistMapper = new ArtistMapper();
        ArtistDTOMapper artistDTOMapper = new ArtistDTOMapper();
        artistService = new ArtistService(artistRepository, artistMapper, artistDTOMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        ArtistDTO artistDTO = new ArtistDTO(1, "Carl Johnson");

        // Act
        ArtistDTO result = artistService.add(artistDTO);

        // Assert
        assertNotNull(result);
        assertEquals(artistDTO.getArtistId(), result.getArtistId());
        assertEquals(artistDTO.getName(), result.getName());
    }

    @Test
    public void testUpdate(){
        // Arrange
        ArtistDTO artistDTO = new ArtistDTO(1, "Old Artist Name");
        artistService.add(artistDTO);
        artistDTO = new ArtistDTO(1, "New Artist Name");

        // Act
        ArtistDTO result = artistService.update(artistDTO);

        // Assert
        assertNotNull(result);
        assertEquals(artistDTO.getArtistId(), result.getArtistId());
        assertEquals(artistDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));

        // Act
        ArtistDTO result = artistService.delete(1);
        ArtistDTO artistDTO = artistService.map(artist);

        // Assert
        assertNotNull(result);
        assertEquals(artistDTO.getArtistId(), result.getArtistId());
        assertEquals(artistDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));

        // Act
        ArtistDTO result = artistService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));

        // Act
        ArtistDTO result = artistService.getById(1);
        ArtistDTO artistDTO = artistService.map(artist);

        // Assert
        assertNotNull(result);
        assertEquals(artistDTO.getArtistId(), result.getArtistId());
        assertEquals(artistDTO.getName(), result.getName());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));

        // Act
        ArtistDTO result = artistService.getById(2);

        // Assert
        assertNull(result);
    }
}