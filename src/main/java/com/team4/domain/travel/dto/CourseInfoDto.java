package com.team4.domain.travel.dto;

import com.team4.domain.travel.domain.Course;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseInfoDto (
        Long id,
        Long nextId,
        LocalDate courseDate,
        Long moveTime,
        String name,
        String address,
        String description
) {
    public static CourseInfoDto of(Course course) {
        return CourseInfoDto.builder()
                .id(course.getId())
                .nextId(course.getNextId().getId())
                .courseDate(course.getCourseDate())
                .moveTime(course.getMoveTime())
                .name(course.getName())
                .address(course.getAddress())
                .description(course.getDescription())
                .build();
    }
}
