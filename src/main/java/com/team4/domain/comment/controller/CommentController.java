package com.team4.domain.comment.controller;

import com.team4.domain.comment.dto.CommentDto;
import com.team4.domain.comment.dto.CommentRequestDto;
import com.team4.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 작성 및 조회 API")
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    @Operation(
            summary = "댓글 등록",
            description = """
            # 새로운 댓글을 작성합니다.
            - 요청 필드
              - `placeId` (Long): 댓글을 다는 장소 ID  
              - `memberId` (Long): 작성자 회원 ID  
              - `isLocal` (boolean): 로컬 여부 (true/false)  
              - `comment` (String): 댓글 내용  
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장소 또는 회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentRequestDto request) {
        CommentDto comment = commentService.addComment(request);
        return ResponseEntity.ok(comment);
    }

}
