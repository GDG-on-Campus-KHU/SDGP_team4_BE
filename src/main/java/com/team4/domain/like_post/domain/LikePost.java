package com.team4.domain.like_post.domain;

import com.team4.domain.member.domain.Member;
import com.team4.domain.travel.domain.Travel;
import com.team4.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "like_posts")
@Getter
@NoArgsConstructor
public class LikePost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public LikePost(Member member, Travel travel) {
        this.member = member;
        this.travel = travel;
    }
}
