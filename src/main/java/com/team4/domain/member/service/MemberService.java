package com.team4.domain.member.service;

import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.dto.MemberInfoDto;
import com.team4.domain.member.domain.Member;
import com.team4.domain.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfoDto showMemberInfo(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        return MemberInfoDto.of(member);
    }


}
