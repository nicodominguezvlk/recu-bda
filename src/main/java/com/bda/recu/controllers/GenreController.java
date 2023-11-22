package com.bda.recu.controllers;

import com.bda.recu.dtos.GenreDTO;
import com.bda.recu.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll(){
        List<GenreDTO> values = this.genreService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<GenreDTO> add(@RequestBody GenreDTO genreDTO){
        GenreDTO createdDTO = this.genreService.add(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenreDTO> delete(@PathVariable int id) {
        GenreDTO response = genreService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<GenreDTO> update(@RequestBody GenreDTO dto) {
        GenreDTO updatedDTO = genreService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
