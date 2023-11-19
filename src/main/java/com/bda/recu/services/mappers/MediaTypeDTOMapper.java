package com.bda.recu.services.mappers;

import com.bda.recu.dtos.MediaTypeDTO;
import com.bda.recu.models.MediaType;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MediaTypeDTOMapper implements Function<MediaType, MediaTypeDTO> {
    @Override
    public MediaTypeDTO apply(MediaType mediaType) {
        return new MediaTypeDTO(
                mediaType.getMediaTypeId(),
                mediaType.getName()
        );
    }
}
