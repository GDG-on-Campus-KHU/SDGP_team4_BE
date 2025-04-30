package com.team4.domain.comment.dto;

import com.team4.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private Long placeId;
    private String nickname;
    private boolean local;
    private String comment;


    public static CommentDto fromEntity(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getPlace().getPlaceId(),
                comment.getMember().getNickname(),
                comment.isLocal(),
                comment.getComment()
        );
    }
}
