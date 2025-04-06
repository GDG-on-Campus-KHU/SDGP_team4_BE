package com.team4.domain.member.api;

import com.team4.domain.member.dto.MemberInfoDto;
import com.team4.domain.member.service.MemberService;
import com.team4.global.jwt.JwtService;
import com.team4.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
* 맴버 API
*
* 1. 맴버 삭제
* 2. 맴버 자신의 정보 조회
* 3. 맴버
* */
@Tag(name = "Member")
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "회원 정보 조회", description = """
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

    @Deprecated
    @GetMapping("/travels")
    @Operation(summary = "회원의 여행지 조회", description = """
            # 회원의 여행지 조회
                        
            사용자가 만든 여행지를 조회합니다.
            썸네일을 입력하지 않는다면, 여행지 코스의 장소 첫이미지를 썸네일로 지정함.
           
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
    public ResponseEntity<CommonResponse<MemberInfoDto>> showMyTravels() {
        String nickname = JwtService.getLoginMemberNickname();
        MemberInfoDto memberInfoDto = memberService.showMemberInfo(nickname);
        return ResponseEntity.ok(CommonResponse.ok(memberInfoDto));
    }

}
