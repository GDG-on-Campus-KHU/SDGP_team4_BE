package com.team4.domain.member.service;

import com.team4.domain.travel.dao.CourseRepository;
import com.team4.domain.travel.domain.Course;
import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.dto.MemberInfoDto;
import com.team4.domain.member.domain.Member;
import com.team4.domain.member.exception.MemberNotFoundException;
import com.team4.domain.travel.dao.TravelRepository;
import com.team4.domain.travel.domain.Travel;
import com.team4.domain.travel.dto.TravelCourseInfoDto;
import com.team4.domain.travel.dto.TravelInfoDto;
import com.team4.domain.travel.exception.TravelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * MemberService
 *
 * 1. 나의 회원정보 조회
 * 2. 나의 회원정보 수정
 * 3. 나의 여행지 전체 조회
 * 4. 나의 여행지 상세 조회
 * 5. 나의 여행지 수정하기
 * 6. 나의 여행지 게시글 등록하기
 * */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TravelRepository travelRepository;
    private final CourseRepository courseRepository;

    public MemberInfoDto showMemberInfo(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        return MemberInfoDto.of(member);
    }

    @Transactional
    public MemberInfoDto editMyProfile(String nickname, MemberInfoDto memberInfoDto) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Member update = member.update(memberInfoDto);
        return MemberInfoDto.of(update);
    }

    public Page<TravelInfoDto> showMyTravels(Pageable pageable, String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Page<Travel> myTravels = travelRepository.findByMember(pageable, member);
        return myTravels.map(TravelInfoDto::of);
    }

    public TravelCourseInfoDto showMyTravelInfo(Long travelId, String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        List<Course> courseList = courseRepository.findAllByTravelId(travelId);
        return TravelCourseInfoDto.of(travel, courseList);
    }

}
