package com.bda.recu.services.mappers;

import com.bda.recu.dtos.ArtistDTO;
import com.bda.recu.models.Artist;
import com.bda.recu.services.AlbumService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class ArtistMapper implements Function<ArtistDTO, Artist> {

    @Override
    public Artist apply(ArtistDTO artistDTO) {
        return new Artist(
                artistDTO.getArtistId(),
                artistDTO.getName(),
                new ArrayList<>()
        );
    }
}
