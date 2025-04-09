package com.team4.domain.post.dto;

import com.team4.domain.post.domain.Post;
import lombok.Builder;

public record PostCreateDto (
        String title,
        String description
) {
    public static Post toEntity(PostCreateDto postCreateDto) {
        return Post.builder()
                .title(postCreateDto.title)
                .description(postCreateDto.description)
                .build();
    }
}
