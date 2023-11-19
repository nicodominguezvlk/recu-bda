package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "genres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreId")
    private int genreId;

    @Column(name = "Name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId")
    private List<Track> trackList;
}
