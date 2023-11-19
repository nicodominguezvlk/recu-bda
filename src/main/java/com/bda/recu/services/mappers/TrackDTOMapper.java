package com.bda.recu.services.mappers;

import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.Track;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrackDTOMapper implements Function<Track, TrackDTO> {
    @Override
    public TrackDTO apply(Track track) {
        return new TrackDTO(
                track.getTrackId(),
                track.getName(),
                track.getAlbum().getAlbumId(),
                track.getMediaType().getMediaTypeId(),
                track.getGenre().getGenreId(),
                track.getComposer(),
                track.getMilliseconds(),
                track.getBytes(),
                track.getUnitPrice()
        );
    }
}
