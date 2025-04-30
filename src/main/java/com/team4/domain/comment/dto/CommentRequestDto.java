package com.team4.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private int commentId;
    private int placeId;
    private int memberId;
    private boolean islocal;
    private String comment;

    public boolean getIslocal() {
        return islocal;
    }
}
