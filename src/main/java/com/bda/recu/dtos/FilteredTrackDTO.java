package com.bda.recu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilteredTrackDTO {
    private int trackId;
    private String name;
    private String albumName;
    private int seconds;
    private double unitPrice;
}
