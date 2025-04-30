package com.team4.domain.place.controller;

import com.team4.domain.comment.dto.CommentDto;
import com.team4.domain.comment.service.CommentService;
import com.team4.domain.place.dto.PlaceDetailDto;
import com.team4.domain.place.dto.PlaceRequestDto;
import com.team4.domain.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Place", description = "장소 상세 조회 및 댓글, 등록 API")
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final CommentService commentService;

    @GetMapping("/{placeId}")
    @Operation(
            summary = "장소 상세 조회",
            description = """
            # 특정 장소의 상세 정보를 조회합니다.
            - 반환 필드: placeId, name, address, latitude, longitude, commentsCnt
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 장소가 존재하지 않음")
    })
    public ResponseEntity<PlaceDetailDto> getPlaceDetail(@PathVariable Long placeId) {
        PlaceDetailDto placeDetail = placeService.getPlaceDetail(placeId);

        if (placeDetail == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(placeDetail);
    }

    @GetMapping("/{placeId}/comments")
    @Operation(
            summary = "장소 댓글 조회",
            description = """
            # 특정 장소에 달린 댓글을 조회합니다.
            - `isLocal` 파라미터로 로컬/전체 댓글 필터링 가능
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공")
    })
    public ResponseEntity<List<CommentDto>> getCommentsByPlace(
            @PathVariable Long placeId,
            @RequestParam(required = false) Boolean isLocal) {

        List<CommentDto> comments;
        if (isLocal == null) {
            comments = commentService.getCommentsByPlace(placeId);
        } else {
            comments = commentService.getCommentsByPlaceAndIsLocal(placeId, isLocal);
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    @Operation(
            summary = "장소 등록",
            description = """
            # 새로운 장소를 등록합니다.
            - 요청 바디: name, address, latitude, longitude
            - 반환: 등록된 장소의 상세 정보
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "장소 등록 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<PlaceDetailDto> addPlace(@RequestBody PlaceRequestDto request) {
        PlaceDetailDto place = placeService.addPlace(request);
        return ResponseEntity.ok(place);
    }

}
