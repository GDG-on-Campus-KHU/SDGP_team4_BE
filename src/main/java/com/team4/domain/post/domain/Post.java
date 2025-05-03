package com.team4.domain.post.domain;

import com.team4.domain.post.dto.PostSimpleDto;
import com.team4.domain.post.dto.PostUpdateDto;
import com.team4.domain.travel.domain.Travel;
import com.team4.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "travel_id", nullable = false, unique = true)
    private Travel travel;

    private String title;
    private String nickname;
    private String description;

    @ElementCollection
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> imgUrls = new ArrayList<>();

    private Long likeCount = 0L;

    @Builder
    public Post(Travel travel, String title, String description,
                Long likeCount, String nickname, List<String> imgUrls) {
        this.travel = travel;
        this.title = title;
        this.description = description;
        this.likeCount = likeCount;
        this.nickname = nickname;
        this.imgUrls = imgUrls;
    }

    public Post update(PostUpdateDto postDto) {
        this.title = postDto.title();
        this.description = postDto.description();
        this.imgUrls = postDto.imgUrls();
        return this;
    }

    public void downLike() {
        this.likeCount--;
    }

    public void upLike() {
        this.likeCount++;
    }
}
