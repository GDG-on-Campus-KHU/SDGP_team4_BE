package com.team4.domain.member.dto;

import com.team4.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @Schema(description = "닉네임", example = "nickname")
        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname,
        @Schema(description = "비밀번호", example = "password1!")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,
        @Schema(description = "거주지", example = "경기도 수원시")
        @NotBlank(message = "거주지는 필수입니다.")
        String region
) {
    public static Member toEntity(SignUpRequest signUpRequest, String password, String refreshToken) {
        return Member.builder()
                .nickname(signUpRequest.nickname())
                .password(password)
                .region(signUpRequest.region())
                .refreshToken(refreshToken)
                .build();
    }
}
