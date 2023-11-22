package com.bda.recu.controllers;

import com.bda.recu.dtos.PlaylistDTO;
import com.bda.recu.dtos.PlaylistTrackDTO;
import com.bda.recu.dtos.TrackDTO;
import com.bda.recu.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAll(){
        List<PlaylistDTO> values = this.playlistService.getAll();
        return ResponseEntity.ok(values);
    }

    @PostMapping
    public ResponseEntity<PlaylistDTO> add(@RequestBody PlaylistDTO playlistDTO){
        PlaylistDTO createdDTO = this.playlistService.add(playlistDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PlaylistDTO> delete(@PathVariable int id) {
        PlaylistDTO response = playlistService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PlaylistDTO> update(@RequestBody PlaylistDTO dto) {
        PlaylistDTO updatedDTO = playlistService.update(dto);
        if (updatedDTO != null) {
            return ResponseEntity.ok(updatedDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add-track")
    public ResponseEntity<PlaylistTrackDTO> addTrackToPlaylist(
            @RequestParam int trackId,
            @RequestParam int playlistId
    ){
        PlaylistTrackDTO response = playlistService.addTrackToPlaylist(trackId, playlistId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-track")
    public ResponseEntity<PlaylistTrackDTO> removeTrackFromPlaylist(
            @RequestParam int trackId,
            @RequestParam int playlistId
    ){
        PlaylistTrackDTO response = playlistService.removeTrackFromPlaylist(trackId, playlistId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<List<TrackDTO>> getAllTracksFromPlaylist(@PathVariable int id){
        List<TrackDTO> values = playlistService.getAllTracksFromPlaylist(id);
        return ResponseEntity.ok(values);
    }
}
