package com.team4.place.dto;

import com.team4.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceDetailDto {
    private int placeId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int commentsCnt;
    private int bestCnt;
    private int goodCnt;
    private int sosoCnt;
    private int badCnt;

    public static PlaceDetailDto fromEntity(Place place) {
        return new PlaceDetailDto(
                place.getPlaceId(),
                place.getName(),
                place.getAddress(),
                place.getLatitude(),
                place.getLongitude(),
                place.getCommentsCnt(),
                place.getBestCnt(),
                place.getGoodCnt(),
                place.getSosoCnt(),
                place.getBadCnt()
        );
    }
}
