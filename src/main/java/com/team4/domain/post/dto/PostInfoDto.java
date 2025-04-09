package com.team4.domain.post.dto;

public record PostInfoDto (
        Long postId,
        String title,
        String description,
        Long likeCount
) {

}
