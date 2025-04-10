package com.team4.domain.member.api;

import com.team4.domain.member.dto.MemberInfoDto;
import com.team4.domain.member.service.MemberService;
import com.team4.domain.post.dto.PostSimpleDto;
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
 * 5. 내가 저장한 게시물 보기
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
            - page: 페이지 번호 (default=0)
            - size: 페이지에 보여지는 수 (default=5)
              
            ## 응답
            
            - 여행지 전체 조회 성공 시, `200`과 함께 아래 정보를 반환합니다.
            - `travel_id`: 여행지 pk
            - `title`: 여행 이름
            - `thumbnail`: 썸네일 사진
            - `start_date`: 여행 시작일자
            - `end_date`: 여행 종료일자
            - `isPost`: 게시글 여부(true=게시글, false=게시글x)
            - `page`: page 관련 정보
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
    @Operation(summary = "회원의 여행지 상세 조회", description = """
            # 회원의 여행지 상세 조회
                        
            회원이 자신의 여행지를 상세조회합니다.
            여행지, 코스 리스트를 전달합니다.
            이떄 코스 리스트는 nextId로 연결되며, nextId가 null이면 마지막 코스입니다.
            
            ## 요청
            ### 경로변수
            - travelId: 여행지 pk
              
            ## 응답
            
            - 여행지 상세 조회 성공 시, `200`과 함께, TravelInfoDto, CourseInfoDtoList를 아래와 같이 반환합니다.
            
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
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    public ResponseEntity<CommonResponse<TravelCourseInfoDto>> showMyTravelInfo(
            @PathVariable Long travelId
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.ok(memberService.showMyTravelInfo(travelId, nickname)));
    }

    @GetMapping("/post")
    @Operation(summary = "좋아요 게시글 전체 조회", description = """
            # 좋아요 게시글 전체 조회
                        
            회원이 좋아요한 게시글을 조회합니다.
            '게시글 전체 조회'와 동일한 데이터를 반환합니다.
              
            ### 쿼리파라미터
            - page: 페이지 번호 (default=0)
            - size: 페이지에 보여지는 수 (default=5)
            
            ## 응답
            
            - 게시글 전체 조회 성공 시, `200`과 함께, 아래의 정보를 페이지네이션하여 반환합니다.
            
            - `postId`: 게시글 pk
            - `title`: 게시글 제목
            - `description`: 게시글 내용
            - `likeCount`: 좋아요 수
            - `isMyLike`: 좋아요 여부(true=좋아요 누름 / false=안누름)
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    public ResponseEntity<CommonResponse<Page<PostSimpleDto>>> showMyPostLike(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.ok(memberService.showMyPostLike(nickname, PageRequest.of(page, size))));
    }

}
