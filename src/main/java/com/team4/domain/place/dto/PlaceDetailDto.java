package com.team4.domain.place.dto;

import com.team4.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlaceDetailDto {
    private Long placeId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int commentsCnt;
    private List<String> imgUrls;

    public static PlaceDetailDto fromEntity(Place place) {
        return new PlaceDetailDto(
                place.getPlaceId(),
                place.getName(),
                place.getAddress(),
                place.getLatitude(),
                place.getLongitude(),
                place.getCommentsCnt(),
                place.getImgUrls()
        );
    }
}
