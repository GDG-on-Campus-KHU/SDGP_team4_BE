package com.team4.domain.post.dto;

import com.team4.domain.member.domain.Member;
import com.team4.domain.post.domain.Post;
import com.team4.domain.travel.domain.Travel;
import com.team4.domain.travel.dto.CourseInfoDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PostInfoDto(
        PostSimpleDto postSimpleDto,
        List<CourseInfoDto> courseInfoDtos,
        boolean isMine
) {
    public static PostInfoDto of(Post post, Travel travel, Member member) {
        return PostInfoDto.builder()
                .postSimpleDto(PostSimpleDto.of(post))
                .courseInfoDtos(
                        travel.getCourseList().stream()
                                .map(CourseInfoDto::of)
                                .collect(Collectors.toList())
                )
                .isMine(travel.getMember().equals(member) ? true : false)
                .build();
    }

}
