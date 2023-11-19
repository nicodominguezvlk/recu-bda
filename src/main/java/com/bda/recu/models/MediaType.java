package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "media_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaTypeId")
    private int mediaTypeId;

    @Column(name = "Name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId")
    private List<Track> trackList;
}
