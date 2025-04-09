package com.team4.domain.member.api;

import com.team4.domain.member.dto.MemberInfoDto;
import com.team4.domain.member.service.MemberService;
import com.team4.domain.travel.dto.TravelCourseInfoDto;
import com.team4.domain.travel.dto.TravelInfoDto;
import com.team4.global.jwt.JwtService;
import com.team4.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
* 맴버 API
 * 1. 나의 회원정보 조회
 * 2. 나의 회원정보 수정
 * 3. 나의 여행지 전체 조회
 * 4. 나의 여행지 상세 조회
* */
@Tag(name = "Member")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "회원정보 조회", description = """
            # 회원정보 조회
                        
            사용자의 정보를 조회합니다.(토큰 기반)
               
            ## 응답
            
            - 맴버 조회 성공 시, `200`과 함께 회원의 닉네임과 거주지를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    public ResponseEntity<CommonResponse<MemberInfoDto>> showMemberInfo() {
        String nickname = JwtService.getLoginMemberNickname();
        MemberInfoDto memberInfoDto = memberService.showMemberInfo(nickname);
        return ResponseEntity.ok(CommonResponse.ok(memberInfoDto));
    }

    @PutMapping
    @Operation(summary = "회원정보 수정", description = """
            # 회원정보 수정
                        
            사용자의 정보를 수정합니다.(토큰 기반)
            
            ## 요청
            
            - nickname: 수정된 닉네임(ex: songsong2)
            - region: 수정된 지역(ex: 경기도 수원시)
               
            ## 응답
            
            - 수정 성공 시, `200`과 함께, 수정된 회원의 닉네임과 거주지를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    public ResponseEntity<CommonResponse<MemberInfoDto>> editMyProfile(
            @Valid @RequestBody MemberInfoDto updateMemberInfo
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        MemberInfoDto memberInfoDto = memberService.editMyProfile(nickname, updateMemberInfo);
        return ResponseEntity.ok(CommonResponse.updated(memberInfoDto));
    }

    @GetMapping("/travel")
    @Operation(summary = "회원의 여행지 전체 조회", description = """
            # 회원의 여행지 조회
                        
            사용자가 만든 여행지를 조회합니다.
            썸네일을 입력하지 않는다면, 여행지 코스의 장소 첫이미지를 썸네일로 지정함.
            ## 요청
            
            ### 쿼리파라미터
            - page: 페이지 번호
            - size: 페이지에 보여지는 여행지 수
              
            ## 응답
            
            - 맴버 조회 성공 시, `200`과 함께 아래 정보를 반환합니다.
            - `travel_id`: pk
            - `start_date`: 여행 시작일자
            - `end_date`: 여행 종료일자
            - `thumbnail`: 썸네일 사진
            - `name`: 여행 이름
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    public ResponseEntity<CommonResponse<Page<TravelInfoDto>>> showMyTravels(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.ok(memberService.showMyTravels(PageRequest.of(page, size), nickname)));
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<CommonResponse<TravelCourseInfoDto>> showMyTravelInfo(
            @PathVariable Long travelId
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.ok(memberService.showMyTravelInfo(travelId, nickname)));
    }

    @GetMapping("/post")
    public ResponseEntity<>
}
