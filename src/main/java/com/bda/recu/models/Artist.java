package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "artists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private int artistId;

    @Column(name = "Name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ArtistId")
    private List<Album> albumList;
}
