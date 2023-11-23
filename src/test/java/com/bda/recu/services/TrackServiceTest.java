package com.bda.recu.services;

import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.*;
import com.bda.recu.repos.*;
import com.bda.recu.services.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TrackServiceTest {

    private TrackService trackService;
    private TrackRepository trackRepository;
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private GenreRepository genreRepository;
    private MediaTypeRepository mediaTypeRepository;


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
        AlbumService albumService = new AlbumService(albumRepository, albumMapper, albumDTOMapper);

        // Genre
        genreRepository = Mockito.mock(GenreRepository.class);
        GenreMapper genreMapper = new GenreMapper();
        GenreDTOMapper genreDTOMapper = new GenreDTOMapper();
        GenreService genreService = new GenreService(genreRepository, genreMapper, genreDTOMapper);

        // MediaType
        mediaTypeRepository = Mockito.mock(MediaTypeRepository.class);
        MediaTypeMapper mediaTypeMapper = new MediaTypeMapper();
        MediaTypeDTOMapper mediaTypeDTOMapper = new MediaTypeDTOMapper();
        MediaTypeService mediaTypeService = new MediaTypeService(mediaTypeRepository, mediaTypeMapper, mediaTypeDTOMapper);

        // Track
        trackRepository = Mockito.mock(TrackRepository.class);
        TrackMapper trackMapper = new TrackMapper(albumService, mediaTypeService, genreService);
        TrackDTOMapper trackDTOMapper = new TrackDTOMapper();
        FilteredTrackDTOMapper filteredTrackDTOMapper = new FilteredTrackDTOMapper();
        trackService = new TrackService(trackRepository, trackMapper, trackDTOMapper, filteredTrackDTOMapper, artistService, mediaTypeService);
    }

    @Test
    public void testAdd(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        TrackDTO trackDTO = new TrackDTO(1, "cabin", 1, 1, 1, "Zimmerman", 200000, 300000, 50.00);

        // Act
        TrackDTO result = trackService.add(trackDTO);

        // Assert
        assertNotNull(result);
        assertEquals(trackDTO.getTrackId(), result.getTrackId());
        assertEquals(trackDTO.getName(), result.getName());
        assertEquals(trackDTO.getAlbumId(), result.getAlbumId());
    }

    @Test
    public void testUpdate(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        TrackDTO trackDTO = new TrackDTO(1, "Old Name", 1, 1, 1, "Zimmerman", 200000, 300000, 50.00);
        trackService.add(trackDTO);
        trackDTO = new TrackDTO(1, "New Name", 1, 1, 1, "Zimmerman", 200000, 300000, 50.00);

        // Act
        TrackDTO result = trackService.update(trackDTO);

        // Assert
        assertNotNull(result);
        assertEquals(trackDTO.getTrackId(), result.getTrackId());
        assertEquals(trackDTO.getName(), result.getName());
        assertEquals(trackDTO.getAlbumId(), result.getAlbumId());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));

        // Act
        TrackDTO result = trackService.delete(1);
        TrackDTO trackDTO = trackService.map(track);

        // Assert
        assertNotNull(result);
        assertEquals(trackDTO.getTrackId(), result.getTrackId());
        assertEquals(trackDTO.getName(), result.getName());
        assertEquals(trackDTO.getAlbumId(), result.getAlbumId());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());

        // Act
        TrackDTO result = trackService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));

        // Act
        TrackDTO result = trackService.getById(1);
        TrackDTO trackDTO = trackService.map(track);

        // Assert
        assertNotNull(result);
        assertEquals(trackDTO.getTrackId(), result.getTrackId());
        assertEquals(trackDTO.getName(), result.getName());
        assertEquals(trackDTO.getAlbumId(), result.getAlbumId());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());

        // Act
        TrackDTO result = trackService.getById(2);

        // Assert
        assertNull(result);
    }
}