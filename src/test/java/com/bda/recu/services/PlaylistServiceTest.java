package com.bda.recu.services;

import com.bda.recu.dtos.PlaylistDTO;
import com.bda.recu.dtos.PlaylistTrackDTO;
import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.*;
import com.bda.recu.repos.*;
import com.bda.recu.services.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistServiceTest {

    private PlaylistService playlistService;
    private PlaylistRepository playlistRepository;
    private GenreRepository genreRepository;
    private MediaTypeRepository mediaTypeRepository;
    private TrackRepository trackRepository;
    private ArtistRepository artistRepository;
    private AlbumRepository albumRepository;

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
        TrackService trackService = new TrackService(trackRepository, trackMapper, trackDTOMapper, filteredTrackDTOMapper, artistService);

        // Playlist
        playlistRepository = Mockito.mock(PlaylistRepository.class);
        PlaylistMapper playlistMapper = new PlaylistMapper();
        PlaylistDTOMapper playlistDTOMapper = new PlaylistDTOMapper();
        playlistService = new PlaylistService(playlistRepository, playlistMapper, playlistDTOMapper, trackService, trackRepository);
    }

    @Test
    public void testAdd(){
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Metal Mix");

        // Act
        PlaylistDTO result = playlistService.add(playlistDTO);

        // Assert
        assertNotNull(result);
        assertEquals(playlistDTO.getPlaylistId(), result.getPlaylistId());
        assertEquals(playlistDTO.getName(), result.getName());
    }

    @Test
    public void testUpdate(){
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Old Playlist Name");
        playlistService.add(playlistDTO);
        playlistDTO = new PlaylistDTO(1, "New Playlist Name");

        // Act
        PlaylistDTO result = playlistService.update(playlistDTO);

        // Assert
        assertNotNull(result);
        assertEquals(playlistDTO.getPlaylistId(), result.getPlaylistId());
        assertEquals(playlistDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Playlist playlist = new Playlist(1, "Metal Mix", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistDTO result = playlistService.delete(1);
        PlaylistDTO playlistDTO = playlistService.map(playlist);

        // Assert
        assertNotNull(result);
        assertEquals(playlistDTO.getPlaylistId(), result.getPlaylistId());
        assertEquals(playlistDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Playlist playlist = new Playlist(1, "Metal Mix", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistDTO result = playlistService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Playlist playlist = new Playlist(1, "Metal Mix", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistDTO result = playlistService.getById(1);
        PlaylistDTO playlistDTO = playlistService.map(playlist);

        // Assert
        assertNotNull(result);
        assertEquals(playlistDTO.getPlaylistId(), result.getPlaylistId());
        assertEquals(playlistDTO.getName(), result.getName());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Playlist playlist = new Playlist(1, "Metal Mix", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistDTO result = playlistService.getById(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void testAddTrackToPlaylist(){
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
        Playlist playlist = new Playlist(1, "Trance", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistTrackDTO result = playlistService.addTrackToPlaylist(1, 1);
        PlaylistTrackDTO playlistTrackDTO = new PlaylistTrackDTO(track.getTrackId(), playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals(playlistTrackDTO.getPlaylistId(), result.getPlaylistId());
        assertEquals(playlistTrackDTO.getTrackId(), result.getTrackId());
    }

    @Test
    public void testRemoveTrackFromPlaylistValid(){
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
        Playlist playlist = new Playlist(1, "Trance", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        List<Track> trackList = new ArrayList<>();
        playlist.setTrackList(trackList);
        List<Playlist> playlistList = new ArrayList<>();
        track.setPlaylistList(playlistList);

        // Act
        PlaylistTrackDTO result = playlistService.removeTrackFromPlaylist(1, 1);
        PlaylistTrackDTO playlistTrackDTO = new PlaylistTrackDTO(track.getTrackId(), playlist.getPlaylistId());

        // Assert
        assertNotNull(result);
        assertEquals(playlistTrackDTO.getPlaylistId(), result.getPlaylistId());
        assertEquals(playlistTrackDTO.getTrackId(), result.getTrackId());
    }

    @Test
    public void testRemoveTrackFromPlaylistNotValid(){
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
        Playlist playlist = new Playlist(1, "Trance", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        List<Track> trackList = new ArrayList<>();
        playlist.setTrackList(trackList);
        List<Playlist> playlistList = new ArrayList<>();
        track.setPlaylistList(playlistList);

        // Act
        PlaylistTrackDTO result = playlistService.removeTrackFromPlaylist(2, 1);
        PlaylistTrackDTO playlistTrackDTO = new PlaylistTrackDTO(track.getTrackId(), playlist.getPlaylistId());

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetAllTracksFromPlaylistFull(){
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
        Playlist playlist = new Playlist(1, "Trance", new ArrayList<>());

        List<Track> trackList = new ArrayList<>();
        trackList.add(track);
        playlist.setTrackList(trackList);
        List<Playlist> playlistList = new ArrayList<>();
        playlistList.add(playlist);
        track.setPlaylistList(playlistList);
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        List<TrackDTO> trackDTOList = playlistService.getAllTracksFromPlaylist(1);

        // Assert
        assertFalse(trackDTOList.isEmpty());
    }

    @Test
    public void testGetAllTracksFromPlaylistEmpty(){
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
        Playlist playlist = new Playlist(1, "Trance", new ArrayList<>());
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        List<TrackDTO> trackDTOList = playlistService.getAllTracksFromPlaylist(1);

        // Assert
        assertTrue(trackDTOList.isEmpty());
    }

    @Test
    public void testGetAllTracksFromPlaylistNotValid(){
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
        Playlist playlist = new Playlist(1, "Trance", new ArrayList<>());

        List<Track> trackList = new ArrayList<>();
        trackList.add(track);
        playlist.setTrackList(trackList);
        List<Playlist> playlistList = new ArrayList<>();
        playlistList.add(playlist);
        track.setPlaylistList(playlistList);
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Mockito.when(playlistRepository.findById(1)).thenReturn(Optional.of(playlist));

        // Act
        List<TrackDTO> trackDTOList = playlistService.getAllTracksFromPlaylist(2);

        // Assert
        assertNull(trackDTOList);
    }
}