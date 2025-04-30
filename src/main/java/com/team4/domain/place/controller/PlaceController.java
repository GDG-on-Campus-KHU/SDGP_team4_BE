package com.team4.domain.place.controller;

import com.team4.domain.comment.dto.CommentDto;
import com.team4.domain.comment.service.CommentService;
import com.team4.domain.place.dto.PlaceDetailDto;
import com.team4.domain.place.dto.PlaceRequestDto;
import com.team4.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final CommentService commentService;

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailDto> getPlaceDetail(@PathVariable int placeId) {
        PlaceDetailDto placeDetail = placeService.getPlaceDetail(placeId);

        if (placeDetail == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(placeDetail);
    }

    @GetMapping("/{placeId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPlace(
            @PathVariable int placeId,
            @RequestParam(required = false) Boolean isLocal) {

        List<CommentDto> comments;
        if (isLocal == null) {
            comments = commentService.getCommentsByPlace(placeId);
        } else {
            comments = commentService.getCommentsByPlaceAndIsLocal(placeId, isLocal);
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<PlaceDetailDto> addPlace(@RequestBody PlaceRequestDto request) {
        PlaceDetailDto place = placeService.addPlace(request);
        return ResponseEntity.ok(place);
    }

}
