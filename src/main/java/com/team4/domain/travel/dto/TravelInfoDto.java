package com.team4.domain.travel.dto;


import com.team4.domain.travel.domain.Travel;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TravelInfoDto(
        Long travelId,
        String title,
        String thumbnail,
        LocalDate startDate,
        LocalDate endDate,
        boolean isPost
){
    public static TravelInfoDto of(Travel travel) {
        return TravelInfoDto.builder()
                .travelId(travel.getId())
                .title(travel.getTitle())
                .thumbnail(travel.getThumbnail())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .isPost(travel.getIsPost())
                .build();
    }
}
