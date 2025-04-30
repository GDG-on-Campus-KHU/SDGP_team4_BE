package com.team4.domain.map.dto;

import com.team4.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MapPinDto {
    private int placeId;
    private double latitude;
    private double longitude;
    private int commentsCnt;

    public static MapPinDto fromEntity(Place place) {
        return new MapPinDto(
                place.getPlaceId(),
                place.getLatitude(),
                place.getLongitude(),
                place.getCommentsCnt()
        );
    }
}
