package com.team4.domain.travel.domain;

import com.team4.domain.member.domain.Member;
import com.team4.domain.post.domain.Post;
import com.team4.domain.travel.dto.TravelCreateDto;
import com.team4.domain.travel.dto.TravelInfoDto;
import com.team4.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * travel entity
 *   name varchar
 *   thumbnail varchar
 *   startdate date
 *   enddate date
 *   isPost bool // 원래는 false, isPost=true -> 내용 채워짐
 *   description varchar
 * */
@Entity
@Table(name = "travels")
@Getter
@NoArgsConstructor
public class Travel extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "travel", orphanRemoval = true)
    private Post post;

    @OneToMany(mappedBy = "travel", orphanRemoval = true)
    private List<Course> courseList = new ArrayList<>();

    private String title;
    private String thumbnail;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPost = false; // -> travel의 게시글 여부 확인 시, post쿼리로 확인하지 않기 위해서.

    @Builder
    public Travel(Long id, Member member, Post post, String title, String thumbnail,
                  LocalDate startDate, LocalDate endDate, boolean isPost) {
        this.id = id;
        this.member = member;
        this.post = post;
        this.title = title;
        this.thumbnail = thumbnail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPost = isPost;
    }

    public Travel update(TravelCreateDto travelInfoDto) {
        this.title = travelInfoDto.title();
        this.thumbnail = travelInfoDto.thumbnail();
        this.startDate = travelInfoDto.startDate();
        this.endDate = travelInfoDto.endDate();
        return this;
    }

    public void setCourseList(List<Course> courses) {
        this.courseList.clear();
        this.courseList.addAll(courses);
    }

    public void updatePost(Post post) {
        this.post = post;
        this.isPost = true;
    }

    public void offPost() {
        this.post = null;
        this.isPost = false;
    }
}
