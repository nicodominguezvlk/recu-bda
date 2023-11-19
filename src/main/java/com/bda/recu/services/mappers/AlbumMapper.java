package com.bda.recu.services.mappers;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.models.Album;
import com.bda.recu.services.ArtistService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class AlbumMapper implements Function<AlbumDTO, Album>{

    private final ArtistService artistService;

    public AlbumMapper(ArtistService artistService){
        this.artistService = artistService;
    }

    @Override
    public Album apply(AlbumDTO albumDTO) {
        return new Album(
                albumDTO.getAlbumId(),
                albumDTO.getTitle(),
                artistService.map(artistService.getById(albumDTO.getArtistId())),
                new ArrayList<>()
        );
    }
}
