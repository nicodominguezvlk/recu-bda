package com.bda.recu.services.mappers;

import com.bda.recu.dtos.PlaylistDTO;
import com.bda.recu.models.Playlist;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class PlaylistMapper implements Function<PlaylistDTO, Playlist> {

    @Override
    public Playlist apply(PlaylistDTO playlistDTO) {
        return new Playlist(
                playlistDTO.getPlaylistId(),
                playlistDTO.getName(),
                new ArrayList<>()
        );
    }
}
