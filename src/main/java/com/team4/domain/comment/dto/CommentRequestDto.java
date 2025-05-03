package com.team4.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long commentId;
    private Long placeId;
    private boolean islocal;
    private String comment;

    public boolean getIslocal() {
        return islocal;
    }
}
