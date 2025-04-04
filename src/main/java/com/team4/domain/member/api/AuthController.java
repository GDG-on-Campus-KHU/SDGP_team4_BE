package com.team4.domain.member.api;

import com.team4.domain.member.dto.LoginRequest;
import com.team4.domain.member.dto.SignUpRequest;
import com.team4.domain.member.service.AuthService;
import com.team4.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    /**
     * 인증/인가 API
     *
     * 1. 회원가입
     * 2. 로그인
     * 3. 비밀번호 수정
     * 4. 회원정보 삭제(soft)
     * 5. 회원정보 강제 삭제
     */

    private final AuthService authService;
    @PostMapping("/signup")
    @Operation
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.created(authService.signup(request)));
    }

    @PostMapping("/login")
    @Operation
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.login(request)));
    }

}
