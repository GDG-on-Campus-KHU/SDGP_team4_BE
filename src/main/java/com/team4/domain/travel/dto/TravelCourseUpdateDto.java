package com.team4.domain.travel.dto;

import java.util.List;

public record TravelCourseUpdateDto (
        TravelCreateDto travelUpdateDto,
        List<CourseCreateDto> courseUpdateDto
) {
}
