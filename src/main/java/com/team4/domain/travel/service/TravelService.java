package com.team4.domain.travel.service;

import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.domain.Member;
import com.team4.domain.member.exception.MemberNotFoundException;
import com.team4.domain.post.domain.Post;
import com.team4.domain.post.dto.PostCreateDto;
import com.team4.domain.post.dto.PostInfoDto;
import com.team4.domain.travel.dao.CourseRepository;
import com.team4.domain.travel.dao.TravelRepository;
import com.team4.domain.travel.domain.Course;
import com.team4.domain.travel.domain.Travel;
import com.team4.domain.travel.dto.CourseCreateDto;
import com.team4.domain.travel.dto.TravelCourseInfoDto;
import com.team4.domain.travel.dto.TravelCourseUpdateDto;
import com.team4.domain.travel.dto.TravelCreateDto;
import com.team4.domain.travel.exception.TravelAuthException;
import com.team4.domain.travel.exception.TravelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final MemberRepository memberRepository;
    private final TravelRepository travelRepository;
    private final CourseRepository courseRepository;

    public Long createTravel(String nickname, TravelCreateDto travelCreateDto) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Travel travel = TravelCreateDto.toEntity(travelCreateDto, member);
        return travelRepository.save(travel).getId();
    }

    @Transactional
    public void createCourse(String nickname, Long travelId, List<CourseCreateDto> courseCreateDto) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        List<Course> courses = courseCreateDto.stream()
                .map(course -> CourseCreateDto.toEntity(course, travel))
                .collect(Collectors.toList());

        for (int i = 0; i < courses.size() - 1; i++) {
            courses.get(i).setNextCourse(courses.get(i + 1)); // 자기참조 연결
        }
        travel.setCourseList(courses);
        courseRepository.saveAll(courses);
    }

    @Transactional
    public TravelCourseInfoDto updateTravel(Long travelId, String nickname, TravelCourseUpdateDto travelCourseInfoDto) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        courseRepository.deleteAllByTravel(travel);
        if(!travel.getMember().getMemberId().equals(member.getMemberId()))
            throw new TravelAuthException();

        List<CourseCreateDto> courseDtos = travelCourseInfoDto.courseUpdateDto();
        List<Course> courses = courseDtos.stream()
                .map(course -> CourseCreateDto.toEntity(course, travel))
                .collect(Collectors.toList());
        for (int i = 0; i < courses.size() - 1; i++) {
            courses.get(i).setNextCourse(courses.get(i + 1)); // 자기참조 연결
        }

        travel.update(travelCourseInfoDto.travelUpdateDto());
        travel.setCourseList(courses);

        return TravelCourseInfoDto.of(travel, courseRepository.saveAll(courses));
    }

    public Long deleteTravel(String nickname, Long travelId) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        if(!travel.getMember().getMemberId().equals(member.getMemberId()))
            throw new TravelAuthException();

        travelRepository.deleteById(travel.getId());
        return travelId;
    }

    public PostInfoDto switchTravelToPost(Long travelId, String nickname, PostCreateDto postCreateDto) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        if(!travel.getMember().getMemberId().equals(member.getMemberId()))
            throw new TravelAuthException();

        Post post = PostCreateDto.toEntity(postCreateDto);
        travel.updatePost(post);

        return new PostInfoDto();
    }
}
