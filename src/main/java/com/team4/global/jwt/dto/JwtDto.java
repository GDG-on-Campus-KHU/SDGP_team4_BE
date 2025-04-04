package com.team4.global.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
