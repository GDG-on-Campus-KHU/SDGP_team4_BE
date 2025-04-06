package com.team4.domain.member.dto;

import com.team4.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MemberInfoDto(
        @Schema(description = "닉네임", example = "nickname")
        String nickname,
        @Schema(description = "거주지", example = "경기도 수원시")
        String region
) {
    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
                .nickname(member.getNickname())
                .region(member.getRegion())
                .build();
    }
}
