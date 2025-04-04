package com.team4.domain.member.dto;

import com.team4.domain.member.entity.Member;
import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String nickname;
    private String region;

    public static Member toEntity(SignUpRequest signUpRequest, String password, String refreshToken) {
        return Member.builder()
                .email(signUpRequest.getEmail())
                .password(password)
                .region(signUpRequest.getRegion())
                .nickname(signUpRequest.getNickname())
                .refreshToken(refreshToken)
                .build();
    }
}
