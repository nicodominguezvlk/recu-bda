package com.bda.recu.services.mappers;

import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Track;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrackDTOMapper implements Function<Track, TrackDTO> {
    @Override
    public TrackDTO apply(Track track) {

        int albumId;
        if (track.getAlbum() != null) { albumId = track.getAlbum().getAlbumId(); }
        else { albumId = 0; }

        return new TrackDTO(
                track.getTrackId(),
                track.getName(),
                albumId,
                track.getMediaType().getMediaTypeId(),
                track.getGenre().getGenreId(),
                track.getComposer(),
                track.getMilliseconds(),
                track.getBytes(),
                track.getUnitPrice()
        );
    }
}
