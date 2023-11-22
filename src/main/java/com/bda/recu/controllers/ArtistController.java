package com.bda.recu.controllers;

import com.bda.recu.dtos.ArtistDTO;
import com.bda.recu.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAll(){
        List<ArtistDTO> values = this.artistService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> add(@RequestBody ArtistDTO artistDTO){
        ArtistDTO createdDTO = this.artistService.add(artistDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ArtistDTO> delete(@PathVariable int id) {
        ArtistDTO response = artistService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ArtistDTO> update(@RequestBody ArtistDTO dto) {
        ArtistDTO updatedDTO = artistService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
