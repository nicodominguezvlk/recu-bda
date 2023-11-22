package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Artist;
import com.bda.recu.repos.AlbumRepository;
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

class AlbumServiceTest {

    private AlbumService albumService;
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;

    @BeforeEach
    public void setup(){
        // Artist
        artistRepository = Mockito.mock(ArtistRepository.class);
        ArtistMapper artistMapper = new ArtistMapper();
        ArtistDTOMapper artistDTOMapper = new ArtistDTOMapper();
        ArtistService artistService = new ArtistService(artistRepository, artistMapper, artistDTOMapper);

        // Album
        albumRepository = Mockito.mock(AlbumRepository.class);
        AlbumMapper albumMapper = new AlbumMapper(artistService);
        AlbumDTOMapper albumDTOMapper = new AlbumDTOMapper();
        albumService = new AlbumService(albumRepository, albumMapper, albumDTOMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        AlbumDTO albumDTO = new AlbumDTO(1, "Album 1", 1);

        // Act
        AlbumDTO result = albumService.add(albumDTO);

        // Assert
        assertNotNull(result);
        assertEquals(albumDTO.getAlbumId(), result.getAlbumId());
        assertEquals(albumDTO.getTitle(), result.getTitle());
        assertEquals(albumDTO.getArtistId(), result.getArtistId());
    }

    @Test
    public void testUpdate(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        AlbumDTO albumDTO = new AlbumDTO(1, "Old Album Name", 1);
        albumService.add(albumDTO);
        albumDTO = new AlbumDTO(1, "New Album Name", 1);

        // Act
        AlbumDTO result = albumService.update(albumDTO);

        // Assert
        assertNotNull(result);
        assertEquals(albumDTO.getAlbumId(), result.getAlbumId());
        assertEquals(albumDTO.getTitle(), result.getTitle());
        assertEquals(albumDTO.getArtistId(), result.getArtistId());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "Album 1", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));

        // Act
        AlbumDTO result = albumService.delete(1);
        AlbumDTO albumDTO = albumService.map(album);

        // Assert
        assertNotNull(result);
        assertEquals(albumDTO.getAlbumId(), result.getAlbumId());
        assertEquals(albumDTO.getTitle(), result.getTitle());
        assertEquals(albumDTO.getArtistId(), result.getArtistId());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "Album 1", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));

        // Act
        AlbumDTO result = albumService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "Album 1", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));

        // Act
        AlbumDTO result = albumService.getById(1);
        AlbumDTO albumDTO = albumService.map(album);

        // Assert
        assertNotNull(result);
        assertEquals(albumDTO.getAlbumId(), result.getAlbumId());
        assertEquals(albumDTO.getTitle(), result.getTitle());
        assertEquals(albumDTO.getArtistId(), result.getArtistId());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Artist artist = new Artist(1, "Carl Johnson", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "Album 1", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));

        // Act
        AlbumDTO result = albumService.getById(2);

        // Assert
        assertNull(result);
    }
}