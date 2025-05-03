package com.team4.domain.post.dto;

import com.team4.domain.post.domain.Post;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PostSimpleDto(
        Long postId,
        String title,
        String nickname,
        LocalDate date,
        String description,
        Long likeCount,
        boolean isMyLike
) {
    public static PostSimpleDto of(Post post, boolean isMyLike) {
        return new PostSimpleDto(
                post.getId(),
                post.getTitle(),
                post.getNickname(),
                post.getModDate().toLocalDate(),
                post.getDescription(),
                post.getLikeCount(),
                isMyLike
        );
    }

    public static PostSimpleDto of(Post post) {
        return new PostSimpleDto(
                post.getId(),
                post.getTitle(),
                post.getNickname(),
                post.getModDate().toLocalDate(),
                post.getDescription(),
                post.getLikeCount(),
                true
        );
    }
}
