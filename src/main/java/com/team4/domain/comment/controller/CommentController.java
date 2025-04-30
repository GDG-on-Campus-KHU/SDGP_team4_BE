package com.team4.domain.comment.controller;

import com.team4.domain.comment.dto.CommentDto;
import com.team4.domain.comment.dto.CommentRequestDto;
import com.team4.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentRequestDto request) {
        CommentDto comment = commentService.addComment(request);
        return ResponseEntity.ok(comment);
    }

}
