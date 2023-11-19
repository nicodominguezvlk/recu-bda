package com.bda.recu.services.mappers;

import com.bda.recu.dtos.PlaylistDTO;
import com.bda.recu.models.Playlist;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlaylistDTOMapper implements Function<Playlist, PlaylistDTO> {
    @Override
    public PlaylistDTO apply(Playlist playlist) {
        return new PlaylistDTO(
                playlist.getPlaylistId(),
                playlist.getName()
        );
    }
}
