package com.bda.recu.controllers;

import com.bda.recu.dtos.MediaTypeDTO;
import com.bda.recu.services.MediaTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mediaTypes")
public class MediaTypeController {

    private final MediaTypeService mediaTypeService;

    public MediaTypeController(MediaTypeService mediaTypeService) {
        this.mediaTypeService = mediaTypeService;
    }

    @GetMapping
    public ResponseEntity<List<MediaTypeDTO>> getAll(){
        List<MediaTypeDTO> values = this.mediaTypeService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<MediaTypeDTO> add(@RequestBody MediaTypeDTO mediaTypeDTO){
        MediaTypeDTO createdDTO = this.mediaTypeService.add(mediaTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MediaTypeDTO> delete(@PathVariable int id) {
        MediaTypeDTO response = mediaTypeService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<MediaTypeDTO> update(@RequestBody MediaTypeDTO dto) {
        MediaTypeDTO updatedDTO = mediaTypeService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
