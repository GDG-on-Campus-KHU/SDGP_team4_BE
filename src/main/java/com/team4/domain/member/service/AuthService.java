package com.team4.domain.member.service;

import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.dto.LoginRequest;
import com.team4.domain.member.dto.LoginResponse;
import com.team4.domain.member.dto.MemberInfoDto;
import com.team4.domain.member.dto.SignUpRequest;
import com.team4.domain.member.domain.Member;
import com.team4.domain.member.exception.LoginFailureException;
import com.team4.domain.member.exception.MemberDuplicatedException;
import com.team4.global.jwt.JwtService;
import com.team4.global.jwt.dto.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByNickname(loginRequest.nickname()).orElseThrow(LoginFailureException::new);
        if(!bCryptPasswordEncoder.matches(loginRequest.password(), member.getPassword()))
            throw new LoginFailureException();

        return new LoginResponse(
                MemberInfoDto.of(member),
                new JwtDto(jwtService.createAccessToken(member.getNickname()), jwtService.createRefreshToken())
        );
    }

    @Transactional
    public LoginResponse signup(SignUpRequest signUpRequest) {
        Optional<Member> exist = memberRepository.findByNickname(signUpRequest.nickname());
        if(exist.isPresent())
            throw new MemberDuplicatedException();

        String password = bCryptPasswordEncoder.encode(signUpRequest.password());
        String refreshToken = jwtService.createRefreshToken();
        Member member = memberRepository.save(SignUpRequest.toEntity(signUpRequest, password, refreshToken));

        return new LoginResponse(
                MemberInfoDto.of(member),
                new JwtDto(jwtService.createAccessToken(member.getNickname()), jwtService.createRefreshToken())
        );
    }

    public void validateNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if(member.isPresent())
            throw new MemberDuplicatedException();
    }
}
