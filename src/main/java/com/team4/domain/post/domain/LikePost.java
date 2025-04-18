package com.team4.domain.post.domain;

import com.team4.domain.member.domain.Member;
import com.team4.domain.post.domain.Post;
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
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public LikePost(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
