package com.team4.domain.member.entity;

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

    private String email;

    private String nickname;

    private String password;

    private String region;

    private String refreshToken;

    @Builder
    public Member(String email, String nickname, String password,
                  String region, String refreshToken) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.region = region;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
