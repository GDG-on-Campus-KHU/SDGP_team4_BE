package com.team4.comment.service;

import com.team4.comment.dto.CommentDto;
import com.team4.comment.dto.CommentRequestDto;
import com.team4.comment.entity.Comment;
import com.team4.member.entity.Member;
import com.team4.place.entity.Place;
import com.team4.comment.repository.CommentRepository;
import com.team4.member.repository.MemberRepository;
import com.team4.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    public List<CommentDto> getCommentsByPlace(int placeId) {
        List<Comment> comments = commentRepository.findByPlace_PlaceId(placeId);
        return comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByPlaceAndIsLocal(int placeId, boolean isLocal) {
        List<Comment> comments = commentRepository.findByPlace_PlaceIdAndIsLocal(placeId, isLocal);
        return comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    public CommentDto addComment(CommentRequestDto request) {
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Comment comment = new Comment(place, member, request.getIslocal(), request.getComment());
        Comment savedComment = commentRepository.save(comment);

        return CommentDto.fromEntity((savedComment));
    }

}
