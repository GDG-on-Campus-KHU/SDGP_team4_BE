package com.team4.domain.travel.domain;

import com.team4.domain.member.domain.Member;
import com.team4.domain.post.domain.Post;
import com.team4.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
@AllArgsConstructor
public class Travel extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "travel", orphanRemoval = true)
    private Post post;

    private String title;
    private String description;
    private String thumbnail;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isPost;

}
