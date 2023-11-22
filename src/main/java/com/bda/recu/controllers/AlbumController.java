package com.bda.recu.controllers;

import com.bda.recu.dtos.AlbumDTO;
import com.bda.recu.services.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAll(){
        List<AlbumDTO> values = this.albumService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> add(@RequestBody AlbumDTO albumDTO){
        AlbumDTO createdDTO = this.albumService.add(albumDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AlbumDTO> delete(@PathVariable int id) {
        AlbumDTO response = albumService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<AlbumDTO> update(@RequestBody AlbumDTO dto) {
        AlbumDTO updatedDTO = albumService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
