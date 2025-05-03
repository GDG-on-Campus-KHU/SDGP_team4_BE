package com.team4.domain.feedback.controller;


import com.team4.domain.feedback.entity.FeedbackType;
import com.team4.domain.feedback.service.PlaceFeedbackService;
import com.team4.global.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Feedback")
@RestController
@RequestMapping("/api/places/{placeId}/feedbacks")
@RequiredArgsConstructor
public class PlaceFeedbackController {

    private final PlaceFeedbackService feedbackService;

    @GetMapping
    @Operation(
            summary = "피드백 집계 및 내 피드백 조회",
            description = """
            # 특정 장소에 대한 피드백 전체 집계 및 내가 남긴 피드백 조회
            - `placeId` 경로변수로 장소 ID 지정  
            ## 응답 필드  
            - `bestCount`, `goodCount`, `sosoCount`, `badCount` (각 피드백 유형별 개수)  
            - `myFeedback` (내가 남긴 피드백 유형, 없으면 해당 키 없음)  
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드백 집계 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장소 또는 회원이 존재하지 않음")
    })
    public ResponseEntity<Map<String, Object>> getFeedbackSummary(
            @PathVariable Long placeId) {
        String nickname = JwtService.getLoginMemberNickname();
        Map<String, Object> result = feedbackService.getFeedbackSummary(placeId, nickname);
        return ResponseEntity.ok(result);
    }


    @PostMapping
    @Operation(
            summary = "피드백 등록/수정",
            description = """
            # 특정 장소에 대한 피드백을 등록하거나, 이미 있으면 수정합니다.
            - `placeId` 경로변수로 장소 ID  
            - `feedbackType` 쿼리파라미터로 피드백 유형 (BEST, GOOD, SOSO, BAD)  
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드백 등록/수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장소 또는 회원이 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "잘못된 피드백 유형")
    })
    public ResponseEntity<Void> addOrUpdateFeedback(
            @PathVariable Long placeId,
            @RequestParam FeedbackType feedbackType) {
        String nickname = JwtService.getLoginMemberNickname();
        feedbackService.addOrUpdateFeedback(placeId, nickname, feedbackType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(
            summary = "피드백 삭제",
            description = """
            # 특정 장소에 남긴 내 피드백을 삭제합니다.
            - `placeId` 경로변수로 장소 ID  
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드백 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장소 또는 회원이 존재하지 않음")
    })
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long placeId) {
        String nickname = JwtService.getLoginMemberNickname();
        feedbackService.deleteFeedback(placeId, nickname);
        return ResponseEntity.ok().build();
    }
}
