package com.team4.global.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record JwtDto(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6Im5pY2tuYW1lIiwic3ViIjoiQWNjZXNzVG9rZW4iLCJpYXQiOjE3NDM5NDIzOTIsImV4cCI6MTc0NzU0MjM5Mn0.vF52DhFcRBe0HYfgUP75NALWxkDTWXbB10awP8V8e6Q")
        String accessToken,
        @Schema(description = "리프래쉬 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJpYXQiOjE3NDM5NDIzOTIsImV4cCI6MTc0NTE1MTk5Mn0.U3QBx4fNSHBayPDglDVOIwWvbi1tYotCzllkD-cJXKk")
        String refreshToken
) {}
