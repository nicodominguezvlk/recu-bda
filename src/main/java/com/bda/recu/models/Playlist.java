package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "playlists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId")
    private int playlistId;

    @Column(name = "Name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "playlist_track",
            joinColumns = @JoinColumn(name = "PlaylistId"),
            inverseJoinColumns = @JoinColumn(name = "TrackId")
    )
    private List<Track> trackList;
}
