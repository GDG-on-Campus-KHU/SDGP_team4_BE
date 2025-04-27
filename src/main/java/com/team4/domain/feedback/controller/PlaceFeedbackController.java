package com.team4.feedback.controller;


import com.team4.feedback.entity.FeedbackType;
import com.team4.feedback.service.PlaceFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/map/places/{placeId}/feedbacks")
@RequiredArgsConstructor
public class PlaceFeedbackController {

    private final PlaceFeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFeedbackSummary(
            @PathVariable int placeId,
            @RequestParam int memberId) {

        Map<String, Object> result = feedbackService.getFeedbackSummary(placeId, memberId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> addOrUpdateFeedback(
            @PathVariable int placeId,
            @RequestParam int memberId,
            @RequestParam FeedbackType feedbackType) {

        feedbackService.addOrUpdateFeedback(placeId, memberId, feedbackType);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable int placeId,
            @RequestParam int memberId) {

        feedbackService.deleteFeedback(placeId, memberId);
        return ResponseEntity.ok().build();
    }
}
