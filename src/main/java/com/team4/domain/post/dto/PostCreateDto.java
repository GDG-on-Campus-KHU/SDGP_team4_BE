package com.team4.domain.post.dto;

import com.team4.domain.post.domain.Post;
import com.team4.domain.travel.domain.Travel;
import lombok.Builder;

import java.util.List;

public record PostCreateDto (
        String title,
        String description,
        List<String> imgUrls
) {
    public static Post toEntity(PostCreateDto postCreateDto, Travel travel, String nickname) {
        return Post.builder()
                .title(postCreateDto.title)
                .description(postCreateDto.description)
                .travel(travel)
                .likeCount(0l)
                .nickname(nickname)
                .build();
    }
}
