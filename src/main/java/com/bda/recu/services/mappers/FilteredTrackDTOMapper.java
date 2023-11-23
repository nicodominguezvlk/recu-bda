package com.bda.recu.services.mappers;

import com.bda.recu.dtos.FilteredTrackDTO;
import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.Track;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FilteredTrackDTOMapper implements Function<Track, FilteredTrackDTO> {

    @Override
    public FilteredTrackDTO apply(Track track) {

        String albumName;
        if (track.getAlbum() != null) { albumName = track.getAlbum().getTitle(); }
        else { albumName = "No album"; }

        return new FilteredTrackDTO(
                track.getTrackId(),
                track.getName(),
                albumName,
                track.getMediaType().getMediaTypeId(),
                track.getMilliseconds() / 1000,
                track.getUnitPrice()
        );
    }
}
