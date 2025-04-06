package com.team4.domain.member.domain;

import com.team4.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    // Ex: 경기도 수원시
    @Column(nullable = false)
    private String region;

    private String refreshToken;

    @Builder
    public Member(String nickname, String password,
                  String region, String refreshToken) {
        this.nickname = nickname;
        this.password = password;
        this.region = region;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
