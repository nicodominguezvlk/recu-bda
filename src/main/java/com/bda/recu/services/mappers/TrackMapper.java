package com.bda.recu.services.mappers;

import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.Track;
import com.bda.recu.services.AlbumService;
import com.bda.recu.services.GenreService;
import com.bda.recu.services.MediaTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class TrackMapper implements Function<TrackDTO, Track> {

    private final AlbumService albumService;
    private final MediaTypeService mediaTypeService;
    private final GenreService genreService;

    public TrackMapper(AlbumService albumService, MediaTypeService mediaTypeService, GenreService genreService) {
        this.albumService = albumService;
        this.mediaTypeService = mediaTypeService;
        this.genreService = genreService;
    }

    @Override
    public Track apply(TrackDTO trackDTO) {
        Album album;
        try{
            album = albumService.map(albumService.getById(trackDTO.getAlbumId()));
        }
        catch (Exception e){
            album = null;
        }

        return new Track(
                trackDTO.getTrackId(),
                trackDTO.getName(),
                album,
                mediaTypeService.map(mediaTypeService.getById(trackDTO.getMediaTypeId())),
                genreService.map(genreService.getById(trackDTO.getGenreId())),
                trackDTO.getComposer(),
                trackDTO.getMilliseconds(),
                trackDTO.getBytes(),
                trackDTO.getUnitPrice(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
