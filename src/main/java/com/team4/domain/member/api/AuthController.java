package com.team4.domain.member.api;

import com.team4.domain.member.dto.LoginRequest;
import com.team4.domain.member.dto.LoginResponse;
import com.team4.domain.member.dto.SignUpRequest;
import com.team4.domain.member.service.AuthService;
import com.team4.global.exception.CommonException;
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

/**
 * 인증/인가 API
 *
 * 1. 회원가입
 * 2. 로그인
 * 3. 닉네임 중복확인
 */

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = """
            # 회원가입
                        
            회원을 생성합니다.
            각 필드의 제약 조건은 다음과 같습니다.

            | 필드명 | 설명 | 제약조건 | 중복확인 | 예시 |
            |--------|------|--------|----------|------|
            | nickname | 사용자의 닉네임 | 2~20자 | N | helloworld |
            | password | 사용자의 비밀번호 | 영문(대소문자), 숫자, 특수문자를 포함한 8~32자 | N | password01! |
            | region | 사용자의 거주지 | 도,시 | Y | 경기도 수원시 |
            
                         
            ## 응답
            
            - 회원 가입 성공 시, `200` 유저의 정보와 토큰(access, refresh)를 반환합니다.
                - 회원가입과 동시에 로그인까지 수행하도록 합니다.
            - 닉네임 중복될 경우, `409` 에러를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "200",
            description = "사용자의 정보와 토큰을 반환합니다.",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "409",
            description = "이미 사용중인 닉네임입니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"이미 사용중인 닉네임입니다.\"\n, \n  \"errorCode\": \"1001\"}")
            )
    )
    public ResponseEntity<CommonResponse<LoginResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(CommonResponse.created(authService.signup(request)));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = """
            # 로그인
                        
            사용자의 닉네임, 비밀번호를 입력하여 로그인을 수행합니다.
            
            ## 응답
            
            - 로그인 성공 시, `200` 유저의 정보와 토큰(access, refresh)를 반환합니다.
            - 로그인 실패 시, 실패 시 `400`에러를 발생합니다.
                - 닉네임을 찾지 못함.
                - 비밀번호 불일치
            """)
    @ApiResponse(
            responseCode = "200",
            description = "사용자의 정보와 토큰을 반환합니다.",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "400",
            description = "이미 사용중인 닉네임입니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"로그인에 실패했습니다.\"\n, \n  \"errorCode\": \"9001\"}")
            )
    )
    public ResponseEntity<CommonResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(CommonResponse.ok(authService.login(request)));
    }

    @PostMapping("/nickname")
    @Operation(summary = "닉네임 중복 확인", description = """
            # 닉네임 중복 확인
                        
            사용자의 닉네임이 중복되지 않는지 확인합니다.
            요청 시, 쿼리파라미터를 사용합니다.
               
            ## 응답
            
            - 로그인 성공 시, `200`을 반환합니다.
            - 로그인 실패 시, 실패 시 `400`에러를 반환합니다.
            """)
    @ApiResponse(
            responseCode = "200",
            useReturnTypeSchema = true
    )
    @ApiResponse(
            responseCode = "400",
            description = "이미 사용중인 닉네임입니다.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommonException.class),
                    examples = @ExampleObject(value = "{\n  \"message\": \"이미 사용중인 닉네임입니다.\"\n, \n  \"errorCode\": \"1002\"}")
            )
    )
    public ResponseEntity<CommonResponse<Void>> validateNickname(@RequestParam String nickname) {
        authService.validateNickname(nickname);
        return ResponseEntity.ok(CommonResponse.ok());
    }

}
