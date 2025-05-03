package com.team4.domain.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlaceRequestDto {

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private List<String> imgUrls;
}
