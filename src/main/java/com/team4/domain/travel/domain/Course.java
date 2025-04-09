package com.team4.domain.travel.domain;

import com.team4.domain.travel.domain.Travel;
import com.team4.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_id")
    private Course nextId;

    private LocalDate courseDate;

    private Long moveTime;

    private String name;

    private String address;

    private String description;

    @Builder
    public Course(Travel travel, Course nextId, LocalDate courseDate, String address,
                  Long moveTime, String name, String description) {
        this.travel = travel;
        this.nextId = nextId;
        this.courseDate = courseDate;
        this.moveTime = moveTime;
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public void setNextCourse(Course nextCourse) {
        this.nextId = nextCourse;
    }
}
