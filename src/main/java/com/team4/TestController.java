package com.team4;

import com.team4.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        String loginMemberEmail = JwtService.getLoginMemberNickname();
        return ResponseEntity.ok(loginMemberEmail + " is healthy!🤣");
    }
}
