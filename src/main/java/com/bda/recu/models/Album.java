package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "albums")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private int albumId;

    @Column(name = "Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "ArtistId")
    private Artist artist;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId")
    private List<Track> trackList;
}
