package com.team4.domain.travel.api;

import com.team4.domain.post.dto.PostCreateDto;
import com.team4.domain.travel.dto.*;
import com.team4.domain.travel.service.TravelService;
import com.team4.global.exception.CommonException;
import com.team4.global.jwt.JwtService;
import com.team4.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Travel API
 * 1. 여행지 생성하기
 * 2. 코스 생성하기
 * 3. 여행지(+코스) -> 게시글 변환하기
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
    @Operation(summary = "여행 생성", description = """
            # 여행 생성
                        
            여행을 생성합니다.
            생성한 여행은 코스없이, 여행에 대한 정보만을 가집니다.
            
            ## 요청
            - `title`: 여행 제목
            - `thumbnail`: 여행 썸네일 (null 가능)
            - `startDate`: 여행 시작일
            - `endDate`: 여행 종료일
            
            ## 응답
            
            - 여행 생성 성공 시, `200`과 여행 pk를 반환합니다.
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    public ResponseEntity<CommonResponse<Long>> createTravel(
            @Valid @RequestBody TravelCreateDto travelCreateDto
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.created(travelService.createTravel(nickname, travelCreateDto)));
    }

    // 받을 때 꼭 정렬된 상태에서 받아야함.
    @PostMapping("/{travelId}/course")
    @Operation(summary = "코스 생성", description = """
            # 코스 생성
                        
            코스를 생성합니다.
            이때, 코스를 중간에 저장할 수 없으며 여행코스를 최종적으로 완성한 경우에 요청합니다.
            
            ## 요청
            `반드시 코스의 순서가 정렬된 상태료 요청을 보내야합니다.`
            
            ### 경로변수
            - `travelId`: 여행 pk
            
            courseCreateDto
            - `name`: 코스 이름
            - `address`: 코스 주소
            - `description`: 코스 메모
            - `courseDate`: 코스 날짜
            - `moveTime`: 이동시간
            
            ## 응답
            
            - 코스 생성 성공 시, `200`과 여행 pk를 반환합니다.
            - 만약 요청한 `여행 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "404",
            description = "해당 여행정보가 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 여행정보가 존재하지 않습니다.\"\n, \n  \"errorCode\": \"2001\"}")
            )
    )
    public ResponseEntity<CommonResponse<Long>> createCourse(
            @PathVariable Long travelId,
            @Valid @RequestBody List<CourseCreateDto> courseList
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        travelService.createCourse(nickname, travelId, courseList);
        return ResponseEntity.ok(CommonResponse.created(travelId));
    }

    @PostMapping("/{travelId}/post")
    @Operation(summary = "여행 -> 게시글 전환", description = """
            # 여행 -> 게시글 전환
                        
            자신이 생성한 여행을 게시글로 전환합니다.
            이때, description에 사진url도 모두 함께 저장합니다.
            
            ## 요청
            
            ### 경로변수
            - `travelId`: 여행 pk
            
            courseCreateDto
            - `title`: 게시글 제목
            - `description`: 게시글 내용
            
            ## 응답
            
            - 여행의 게시글 전환 성공 시, `200`과 여행 pk를 반환합니다.
            - 만약 요청한 `여행 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            - 전환하는 자가 본인이 아니라면, `401`과 함께 에러 메세지를 반환합니다.
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "404",
            description = "해당 여행정보가 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 여행정보가 존재하지 않습니다.\"\n, \n  \"errorCode\": \"2001\"}")
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "여행정보에 접근할 권한이 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"여행정보에 접근할 권한이 없습니다.\"\n, \n  \"errorCode\": \"2002\"}")
            )
    )
    public ResponseEntity<CommonResponse<Long>> switchTravelToPost(
            @PathVariable Long travelId,
            @Valid @RequestBody PostCreateDto postCreateDto
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        travelService.switchTravelToPost(travelId, nickname, postCreateDto);
        return ResponseEntity.ok(CommonResponse.created(travelId));
    }

    @PutMapping("/{travelId}")
    @Operation(summary = "여행 수정", description = """
            # 여행 수정
                        
            자신의 여행정보를 수정합니다.
            여행지, 코스 리스트로 요청하며, 이또한 코스가 순서대로 정렬되어야합니다.
            
            ## 요청
            ### 경로변수
            - travelId: 여행지 pk
              
            TravelInfoDto
            - `travel_id`: 여행지 pk
            - `title`: 여행 이름
            - `thumbnail`: 썸네일 사진
            - `start_date`: 여행 시작일자
            - `end_date`: 여행 종료일자
            - `isPost`: 게시글 여부(true=게시글, false=게시글x)
            
            CourseInfoDtoList의 원소
            - `id`: 코스 pk
            - `next_id`: 다음 코스 id
            - `courseDate`: 코스 여행날짜 (xxxx.xx.xx)
            - `moveTime`: 이동시간
            - `name`: 코스 이름
            - `address`: 코스 주소
            - `description`: 코스에 대한 짧은 메모
            
            ## 응답
            
            - 여행지 수정 성공 시, `200`과 함께, 수정된 TravelInfoDto, CourseInfoDtoList를 동일하게 반환합니다.
            - 만약 요청한 `여행 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            - 수정하는 자가 본인이 아니라면, `401`과 함께 에러 메세지를 반환합니다.
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "404",
            description = "해당 여행정보가 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 여행정보가 존재하지 않습니다.\"\n, \n  \"errorCode\": \"2001\"}")
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "여행정보에 접근할 권한이 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"여행정보에 접근할 권한이 없습니다.\"\n, \n  \"errorCode\": \"2002\"}")
            )
    )
    public ResponseEntity<CommonResponse<TravelCourseInfoDto>> updateTravel(
            @PathVariable Long travelId,
            @Valid @RequestBody TravelCourseUpdateDto travelCourseInfoDto
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.updated(travelService.updateTravel(travelId, nickname, travelCourseInfoDto)));
    }

    @DeleteMapping("/{travelId}")
    @Operation(summary = "여행 삭제", description = """
            # 여행 삭제
                        
            자신의 여행정보를 삭제합니다.
            
            ## 요청
            ### 경로변수
            - travelId: 여행지 pk
            
            ## 응답
            
            - 여행지 삭제 성공 시, `200`을 반환합니다.
            - 만약 요청한 `여행 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            - 삭제하는 자가 본인이 아니라면, `401`과 함께 에러 메세지를 반환합니다.
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "404",
            description = "해당 여행정보가 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 여행정보가 존재하지 않습니다.\"\n, \n  \"errorCode\": \"2001\"}")
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "여행정보에 접근할 권한이 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"여행정보에 접근할 권한이 없습니다.\"\n, \n  \"errorCode\": \"2002\"}")
            )
    )
    public ResponseEntity<CommonResponse<Void>> deleteMyTravel(
            @PathVariable Long travelId
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        travelService.deleteTravel(nickname, travelId);
        return ResponseEntity.ok(CommonResponse.delete());
    }
}
