package com.bda.recu.services.mappers;

import com.bda.recu.dtos.MediaTypeDTO;
import com.bda.recu.models.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public class MediaTypeMapper implements Function<MediaTypeDTO, MediaType> {

    @Override
    public MediaType apply(MediaTypeDTO mediaTypeDTO) {
        return new MediaType(
                mediaTypeDTO.getMediaTypeId(),
                mediaTypeDTO.getName(),
                new ArrayList<>()
        );
    }
}
