package com.team4.domain.travel.dto;

import com.team4.domain.travel.domain.Course;
import com.team4.domain.travel.domain.Travel;
import lombok.Builder;

import java.util.List;

@Builder
public record TravelCourseInfoDto (
        TravelInfoDto travelInfoDto,
        List<CourseInfoDto> courseInfoDtoList
){
    public static TravelCourseInfoDto of(Travel travel, List<Course> courseList) {
        return TravelCourseInfoDto.builder()
                .travelInfoDto(TravelInfoDto.of(travel))
                .courseInfoDtoList(courseList.stream().map(CourseInfoDto::of).toList())
                .build();
    }
}
