package com.bda.recu.controllers;

import com.bda.recu.dtos.FilteredTrackDTO;
import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.services.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getAll(){
        List<TrackDTO> values = this.trackService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<TrackDTO> add(@RequestBody TrackDTO trackDTO){
        TrackDTO createdDTO = this.trackService.add(trackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TrackDTO> delete(@PathVariable int id) {
        TrackDTO response = trackService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<TrackDTO> update(@RequestBody TrackDTO dto) {
        TrackDTO updatedDTO = trackService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<FilteredTrackDTO>> getAllWithArtist(
            @RequestParam int artistId
    ){
        List<FilteredTrackDTO> values = this.trackService.getAllWithArtist(artistId);
        return ResponseEntity.ok(values);
    }
}
