package com.team4.domain.member.dto;

import com.team4.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoDto {
    private String email;
    private String nickname;
    private String region;

    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .region(member.getRegion())
                .build();
    }

}
