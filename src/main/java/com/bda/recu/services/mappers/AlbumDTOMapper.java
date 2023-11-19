package com.bda.recu.services.mappers;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.models.Album;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AlbumDTOMapper implements Function<Album, AlbumDTO> {

    @Override
    public AlbumDTO apply(Album album) {
        return new AlbumDTO(
                album.getAlbumId(),
                album.getTitle(),
                album.getArtist().getArtistId()
        );
    }
}
