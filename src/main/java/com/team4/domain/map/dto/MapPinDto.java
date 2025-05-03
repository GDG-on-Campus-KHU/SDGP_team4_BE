package com.team4.domain.map.dto;

import com.team4.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MapPinDto {
    private Long placeId;
    private double latitude;
    private double longitude;
    private int commentsCnt;
    private int bestCount;
    private int goodCount;
    private int sosoCount;
    private int badCount;

    public static MapPinDto fromEntity(
            Place place,
            int bestCount,
            int goodCount,
            int sosoCount,
            int badCount
    ) {
        return new MapPinDto(
                place.getPlaceId(),
                place.getLatitude(),
                place.getLongitude(),
                place.getCommentsCnt(),
                bestCount,
                goodCount,
                sosoCount,
                badCount
        );
    }
}
