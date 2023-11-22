package com.bda.recu.services;

import com.bda.recu.dtos.PlaylistDTO;
import com.bda.recu.dtos.PlaylistTrackDTO;
import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.Playlist;
import com.bda.recu.models.Track;
import com.bda.recu.repos.PlaylistRepository;
import com.bda.recu.repos.TrackRepository;
import com.bda.recu.services.mappers.PlaylistDTOMapper;
import com.bda.recu.services.mappers.PlaylistMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper entityMapper;
    private final PlaylistDTOMapper DTOmapper;
    private final TrackService trackService;
    private final TrackRepository trackRepository;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMapper entityMapper, PlaylistDTOMapper DTOmapper, TrackService trackService, TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.entityMapper = entityMapper;
        this.DTOmapper = DTOmapper;
        this.trackService = trackService;
        this.trackRepository = trackRepository;
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
        return entity.map(DTOmapper).orElse(null);
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

    public PlaylistTrackDTO addTrackToPlaylist(int trackId, int playlistId){
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        Track track = trackService.map(trackService.getById(trackId));

        List<Track> trackList = playlist.getTrackList();
        trackList.add(track);
        playlist.setTrackList(trackList);

        playlistRepository.save(playlist);

        return new PlaylistTrackDTO(trackId, playlistId);
    }

    public PlaylistTrackDTO removeTrackFromPlaylist(int trackId, int playlistId){
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        Track track = trackRepository.findById(trackId).orElse(null);

        if (playlist == null){
            return null;
        }
        List<Track> trackList = playlist.getTrackList();
        trackList.remove(track);

        if (track == null){
            return null;
        }
        List<Playlist> playlistList = track.getPlaylistList();
        playlistList.remove(playlist);

        playlist.setTrackList(trackList);
        track.setPlaylistList(playlistList);

        playlistRepository.save(playlist);
        trackRepository.saveAndFlush(track);

        return new PlaylistTrackDTO(trackId, playlistId);
    }

    public List<TrackDTO> getAllTracksFromPlaylist(int playlistId){
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);

        if (playlist == null){
            return null;
        }
        List<Track> trackList = playlist.getTrackList();
        List<TrackDTO> trackDTOList = new ArrayList<>();

        for (Track track : trackList){
            TrackDTO trackDTO = trackService.map(track);
            trackDTOList.add(trackDTO);
        }

        return trackDTOList;
    }
}
