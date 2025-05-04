package com.team4.domain.comment.service;

import com.team4.domain.comment.dto.CommentDto;
import com.team4.domain.comment.dto.CommentRequestDto;
import com.team4.domain.comment.dto.CommentSummaryDto;
import com.team4.domain.comment.entity.Comment;
import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.domain.Member;
import com.team4.domain.place.entity.Place;
import com.team4.domain.comment.repository.CommentRepository;
import com.team4.domain.place.repository.PlaceRepository;
import com.team4.global.gemini.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    private final GeminiService geminiService;

    public List<CommentDto> getCommentsByPlace(Long placeId) {
        List<Comment> comments = commentRepository.findByPlace_PlaceId(placeId);
        return comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByPlaceAndIsLocal(Long placeId, boolean isLocal) {
        List<Comment> comments = commentRepository.findByPlace_PlaceIdAndIsLocal(placeId, isLocal);
        return comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto addComment(CommentRequestDto request, String nickname) {
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Comment comment = new Comment(place, member, request.getIslocal(), request.getComment());
        Comment savedComment = commentRepository.save(comment);
        place.commentsCntUp();

        return CommentDto.fromEntity((savedComment));
    }

    public CommentSummaryDto summaryComments(String prompt, Long placeId) {
        List<Comment> comments = commentRepository.findByPlace_PlaceId(placeId);
        // 댓글 본문만 추출해 줄 단위로 이어붙임
        String commentText = comments.stream()
                .map(Comment::getComment)
                .collect(Collectors.joining("\n"));

        // 프롬프트 + 댓글내용 전달
        String fullPrompt = prompt + "\n\n" + commentText;

        return new CommentSummaryDto(geminiService.getContents(fullPrompt));
    }

}
