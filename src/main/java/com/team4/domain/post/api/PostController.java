package com.team4.domain.post.api;

import com.team4.domain.post.dto.PostInfoDto;
import com.team4.domain.post.dto.PostLikeDto;
import com.team4.domain.post.dto.PostSimpleDto;
import com.team4.domain.post.dto.PostUpdateDto;
import com.team4.domain.post.service.PostService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 API
 *
 * 1. 게시글 전체 조회(pagination)
 * 2. 게시글 상세 조회
 * 3. 게시글 내리기(삭제는 아님. 삭제는 TRAVEl 로만)
 * 4. 게시글 수정하기(게시글 내용만)
 * 5. 게시글 저장 or 저장안하기 -> 유저가 찜한 목록으로
 */

@Tag(name = "Post")
@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping
    @Operation(summary = "게시글 전체 조회", description = """
            # 게시글 전체 조회
                        
            게시글을 조회합니다. 
            
            ## 요청
            ### 쿼리파라미터
            - `page`: 페이지 번호 (default=0)
            - `size`: 페이지에 보여지는 수 (default=5)
            
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
    public ResponseEntity<CommonResponse<Page<PostSimpleDto>>> showPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.ok(postService.showPosts(nickname, PageRequest.of(page, size))));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회", description = """
            # 게시글 상세 조회
                        
            게시글 하나를 상세 조회합니다.
            이때, 게시글정보와 코스정보를 나눠서 응답합니다.
            
            ## 요청
            ### 경로변수
            - `postId`: 게시글 pk
            
            ## 응답
            
            - 게시글 상세 조회 성공 시, `200`과 함께, postSimpleDto, CourseInfoDtoList를 아래와 같이 반환합니다.
            - 만약 요청한 `게시글 pk`에 대해 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            
            postSimpleDto
            - `postId`: 게시글 pk
            - `title`: 게시글 제목
            - `description`: 게시글 내용
            - `likeCount`: 좋아요 수
            - `isMyLike`: 좋아요 여부(true=좋아요 누름 / false=안누름)
            
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
    @ApiResponse(
            responseCode = "404",
            description = "해당 게시글이 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 게시글이 존재하지 않습니다.\"\n, \n  \"errorCode\": \"3001\"}")
            )
    )
    public ResponseEntity<CommonResponse<PostInfoDto>> showPostInfo(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(CommonResponse.ok(postService.showPostInfo(postId)));
    }

    @PutMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = """
            # 게시글 수정
                        
            게시글을 수정합니다.
            이때, 게시글의 값만 수정할 수 있습니다. 코스는 여행정보에서 수정가능합니다.
            
            ## 요청
            ### 경로변수
            - `postId`: 게시글 pk
            
            ### 요청 바디
            - `title`: 게시글 제목
            - `description`: 게시글 내용
            
            ## 응답
            
            - 게시글 수정 성공 시, `200`과 함께, `게시글 상세보기`와 동일하게 반환합니다.
            - 만약 요청한 `게시글 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            - 게시글을 수정하는 자가 본인이 아니라면, `401`과 함께 에러 메세지를 반환합니다.
            
            postSimpleDto
            - `postId`: 게시글 pk
            - `title`: 게시글 제목
            - `description`: 게시글 내용
            - `likeCount`: 좋아요 수
            - `isMyLike`: 좋아요 여부(true=좋아요 누름 / false=안누름)
            
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
    @ApiResponse(
            responseCode = "404",
            description = "해당 게시글이 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 게시글이 존재하지 않습니다.\"\n, \n  \"errorCode\": \"3001\"}")
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "게시글에 접근할 권한이 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"게시글에 접근할 권한이 없습니다.\"\n, \n  \"errorCode\": \"3002\"}")
            )
    )
    public ResponseEntity<CommonResponse<PostInfoDto>> editPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateDto postDto
    ) {
        return ResponseEntity.ok(CommonResponse.updated(postService.editPostInfo(postId, postDto)));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", description = """
            # 게시글 삭제
                        
            게시글을 숨깁니다.
            게시글 정보의 정보만 삭제하고, 여행정보는 삭제되지 않습니다.
            
            ## 요청
            ### 경로변수
            - `postId`: 게시글 pk
            
            ## 응답
            - 게시글 삭제 성공 시, `200`을 반환합니다.
            - 만약 요청한 `게시글 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            - 게시글을 수정하는 자가 본인이 아니라면, `401`과 함께 에러 메세지를 반환합니다.
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "404",
            description = "해당 게시글이 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 게시글이 존재하지 않습니다.\"\n, \n  \"errorCode\": \"3001\"}")
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "게시글에 접근할 권한이 없습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"게시글에 접근할 권한이 없습니다.\"\n, \n  \"errorCode\": \"3002\"}")
            )
    )
    public ResponseEntity<CommonResponse<PostInfoDto>> offPost(
            @PathVariable Long postId
    ) {
        postService.offPost(postId);
        return ResponseEntity.ok(CommonResponse.delete());
    }

    @PostMapping("/{postId}")
    @Operation(summary = "게시글 좋아요", description = """
            # 게시글 좋아요
                        
            게시글에 좋아요를 누르고, 게시글의 좋아요 수를 증가시킵니다.
            게시글에 좋아요를 이미 눌렀다면, 게시글의 좋아요 수를 감소시킵니다.
            
            ## 요청
            ### 경로변수
            - `postId`: 게시글 pk
            
            ## 응답
            
            - 게시글 삭제 성공 시, `200`을 반환합니다.
            - 만약 요청한 `게시글 pk`가 존재하지 않는다면, `404`과 함께 에러 메세지를 반환합니다.
            
            - `postId`: 게시글 pk
            - `likeCount`: 현재 좋아요 수
            
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "404",
            description = "해당 게시글이 존재하지 않습니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"해당 게시글이 존재하지 않습니다.\"\n, \n  \"errorCode\": \"3001\"}")
            )
    )
    public ResponseEntity<CommonResponse<PostLikeDto>> changePostStatus(
            @PathVariable Long postId
    ) {
        String nickname = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(CommonResponse.ok(postService.changePostStatus(postId, nickname)));
    }

}
