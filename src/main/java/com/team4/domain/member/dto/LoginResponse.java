package com.team4.domain.member.dto;

import com.team4.global.jwt.dto.JwtDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private MemberInfoDto memberInfoDto;
    private JwtDto jwtDto;
}
