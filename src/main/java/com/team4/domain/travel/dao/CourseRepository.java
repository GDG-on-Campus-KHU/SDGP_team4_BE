package com.team4.domain.travel.dao;

import com.team4.domain.travel.domain.Course;
import com.team4.domain.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByTravelId(Long travelId);

    void deleteAllByTravel(Travel travel);
}
