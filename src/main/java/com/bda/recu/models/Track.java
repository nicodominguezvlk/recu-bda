package com.bda.recu.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "tracks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private int trackId;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "MediaTypeId")
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "GenreId")
    private Genre genre;

    @Column(name = "Composer")
    private String composer;

    @Column(name = "Milliseconds")
    private int milliseconds;

    @Column(name= "Bytes")
    private int bytes;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrackId")
    private List<InvoiceItem> invoiceItemList;

    @ManyToMany(mappedBy = "trackList")
    private List<Playlist> playlistList;
}
