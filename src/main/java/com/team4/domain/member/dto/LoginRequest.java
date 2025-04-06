package com.team4.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record LoginRequest(
        @Schema(description = "닉네임", example = "nickname")
        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname,
        @Schema(description = "비밀번호", example = "password")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {

}
