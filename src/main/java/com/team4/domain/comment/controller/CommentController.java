package com.team4.domain.comment.controller;

import com.team4.domain.comment.dto.CommentDto;
import com.team4.domain.comment.dto.CommentRequestDto;
import com.team4.domain.comment.dto.CommentSummaryDto;
import com.team4.domain.comment.service.CommentService;
import com.team4.global.jwt.JwtService;
import com.team4.global.response.CommonResponse;
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
@Tag(name = "Comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(
            summary = "댓글 등록",
            description = """
            # 새로운 댓글을 작성합니다.
            - 요청 필드
              - `placeId` (Long): 댓글을 다는 장소 ID  
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
        String nickname = JwtService.getLoginMemberNickname();
        CommentDto comment = commentService.addComment(request, nickname);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{placeId}/summary")
    @Operation(
            summary = "댓글 요약",
            description = """
            # 댓글 요약
            - gemini를 통해서 댓글의 내용을 요약해서 보여줍니다.
              
            ## 요청
            ### 경로변수
            - `placeId` (Long): 장소 ID
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 요약 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장소를 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    public ResponseEntity<CommonResponse<CommentSummaryDto>> summaryComment(
            @PathVariable(name = "placeId") Long placeId
    ) {
        CommentSummaryDto summary = commentService.summaryComments(
                "아래는 댓글의 모든 내용을 데이터베이스에서 가져온 내용이야. 댓글을 모두 읽어보고 댓글의 내용을 요약해서 줘. 요약해줄떄는 어떤 말도 해줄필요 없고, 줄바꿈없이 요약해서 보여줘. 예시: 전체적으로는 이러이러 하하고 해! 그 중 이러이런 의견이 가장 많고, 이러이러 해서 이러이러 하하고 해. 이러이러 해서 이러이러 하다고 해!... 이런식으로",
                placeId);
        return ResponseEntity.ok(CommonResponse.ok(summary));
    }
}
