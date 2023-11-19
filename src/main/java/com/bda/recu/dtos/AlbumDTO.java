package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private int albumId;
    private String title;
    private int artistId;
}
