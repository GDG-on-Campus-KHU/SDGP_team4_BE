package com.team4.domain.travel.api;

import com.team4.domain.post.dto.PostCreateDto;
import com.team4.domain.travel.dto.*;
import com.team4.domain.travel.service.TravelService;
import com.team4.global.jwt.JwtService;
import com.team4.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Travel API
 * 1. 여행지 생성하기
 * 3. 코스 생성하기
 * 4. 여행지, 코스 수정하기
 * 5. 여행지 삭제하기
 * */
@Tag(name = "Travel")
@RestController
@RequestMapping("/api/v1/travel")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ResponseEntity<CommonResponse<Long>> createTravel(
            @Valid @RequestBody TravelCreateDto travelCreateDto
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.created(travelService.createTravel(nickname, travelCreateDto)));
    }

    // 받을 때 꼭 정렬된 상태에서 받아야함.
    @PostMapping("/{travelId}/course")
    public ResponseEntity<CommonResponse<Long>> createCourse(
            @PathVariable Long travelId,
            @Valid @RequestBody List<CourseCreateDto> courseList
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        travelService.createCourse(nickname, travelId, courseList);
        return ResponseEntity.ok(CommonResponse.created(travelId));
    }

    @PostMapping("/{travelId}/post")
    public ResponseEntity<CommonResponse<Long>> switchTravelToPost(
            @PathVariable Long travelId,
            @Valid @RequestBody PostCreateDto postCreateDto
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        travelService.switchTravelToPost(travelId, nickname, postCreateDto);
        return ResponseEntity.ok(CommonResponse.created(travelId));
    }

    @PutMapping("/{travelId}")
    public ResponseEntity<CommonResponse<TravelCourseInfoDto>> updateTravel(
            @PathVariable Long travelId,
            @Valid @RequestBody TravelCourseUpdateDto travelCourseInfoDto
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.updated(travelService.updateTravel(travelId, nickname, travelCourseInfoDto)));
    }

    @DeleteMapping("/{travelId}")
    public ResponseEntity<CommonResponse<Long>> deleteMyTravel(
            @PathVariable Long travelId
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.delete(travelService.deleteTravel(nickname, travelId)));
    }
}
