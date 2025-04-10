package com.team4.domain.travel.dto;

import com.team4.domain.travel.domain.Course;
import com.team4.domain.travel.domain.Travel;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseCreateDto(
        String name,
        String address,
        String description,
        LocalDate courseDate,
        Long moveTime
) {
    public static Course toEntity(CourseCreateDto courseCreateDto, Travel travel) {
        return Course.builder()
                .travel(travel)
                .courseDate(courseCreateDto.courseDate)
                .moveTime(courseCreateDto.moveTime)
                .name(courseCreateDto.name)
                .address(courseCreateDto.address)
                .description(courseCreateDto.description)
                .build();
    }
}
