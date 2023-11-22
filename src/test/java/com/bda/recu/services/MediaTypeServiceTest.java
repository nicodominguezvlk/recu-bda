package com.bda.recu.services;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.dtos.MediaTypeDTO;
import com.bda.recu.models.Album;
import com.bda.recu.models.MediaType;
import com.bda.recu.repos.MediaTypeRepository;
import com.bda.recu.services.mappers.AlbumDTOMapper;
import com.bda.recu.services.mappers.AlbumMapper;
import com.bda.recu.services.mappers.MediaTypeDTOMapper;
import com.bda.recu.services.mappers.MediaTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MediaTypeServiceTest {

    private MediaTypeRepository mediaTypeRepository;
    private MediaTypeService mediaTypeService;

    @BeforeEach
    public void setup(){
        // MediaType
        mediaTypeRepository = Mockito.mock(MediaTypeRepository.class);
        MediaTypeMapper mediaTypeMapper = new MediaTypeMapper();
        MediaTypeDTOMapper mediaTypeDTOMapper = new MediaTypeDTOMapper();
        mediaTypeService = new MediaTypeService(mediaTypeRepository, mediaTypeMapper, mediaTypeDTOMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        MediaTypeDTO mediaTypeDTO = new MediaTypeDTO(1, "MP3");

        // Act
        MediaTypeDTO result = mediaTypeService.add(mediaTypeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(mediaTypeDTO.getMediaTypeId(), result.getMediaTypeId());
        assertEquals(mediaTypeDTO.getName(), result.getName());
    }

    @Test
    public void testUpdate(){
        // Arrange
        MediaTypeDTO mediaTypeDTO = new MediaTypeDTO(1, "Old MediaType Name");
        mediaTypeService.add(mediaTypeDTO);
        mediaTypeDTO = new MediaTypeDTO(1, "New MediaType Name");

        // Act
        MediaTypeDTO result = mediaTypeService.update(mediaTypeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(mediaTypeDTO.getMediaTypeId(), result.getMediaTypeId());
        assertEquals(mediaTypeDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));

        // Act
        MediaTypeDTO result = mediaTypeService.delete(1);
        MediaTypeDTO mediaTypeDTO = mediaTypeService.map(mediaType);

        // Assert
        assertNotNull(result);
        assertEquals(mediaTypeDTO.getMediaTypeId(), result.getMediaTypeId());
        assertEquals(mediaTypeDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));

        // Act
        MediaTypeDTO result = mediaTypeService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));

        // Act
        MediaTypeDTO result = mediaTypeService.getById(1);
        MediaTypeDTO mediaTypeDTO = mediaTypeService.map(mediaType);

        // Assert
        assertNotNull(result);
        assertEquals(mediaTypeDTO.getMediaTypeId(), result.getMediaTypeId());
        assertEquals(mediaTypeDTO.getName(), result.getName());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));

        // Act
        MediaTypeDTO result = mediaTypeService.getById(2);

        // Assert
        assertNull(result);
    }
}