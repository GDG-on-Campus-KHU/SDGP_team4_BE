package com.team4.domain.travel.dto;

import com.team4.domain.member.domain.Member;
import com.team4.domain.travel.domain.Travel;

import java.time.LocalDate;

public record TravelCreateDto(
        String title,
        String thumbnail,
        LocalDate startDate,
        LocalDate endDate
) {
    public static Travel toEntity(TravelCreateDto travelCreateDto, Member member) {
        return Travel.builder()
                .member(member)
                .title(travelCreateDto.title)
                .thumbnail(travelCreateDto.thumbnail)
                .startDate(travelCreateDto.startDate)
                .endDate(travelCreateDto.endDate)
                .isPost(false)
                .build();
    }
}
