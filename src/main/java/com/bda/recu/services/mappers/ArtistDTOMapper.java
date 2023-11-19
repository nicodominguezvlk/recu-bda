package com.bda.recu.services.mappers;

import com.bda.recu.dtos.ArtistDTO;
import com.bda.recu.models.Artist;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArtistDTOMapper implements Function<Artist, ArtistDTO> {
    @Override
    public ArtistDTO apply(Artist artist) {
        return new ArtistDTO(
                artist.getArtistId(),
                artist.getName()
        );
    }
}
