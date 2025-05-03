package com.team4.domain.feedback.service;


import com.team4.domain.feedback.entity.FeedbackType;
import com.team4.domain.feedback.entity.PlaceFeedback;
import com.team4.domain.feedback.repository.PlaceFeedbackRepository;
import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.domain.Member;
import com.team4.domain.place.entity.Place;
import com.team4.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceFeedbackService {

    private final PlaceRepository placeRepository;
    private final PlaceFeedbackRepository feedbackRepository;
    private final MemberRepository memberRepository;

    public Map<String, Object> getFeedbackSummary(Long placeId, String nickname) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Map<String, Object> result = new HashMap<>();
        result.put("bestCount", feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.BEST));
        result.put("goodCount", feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.GOOD));
        result.put("sosoCount", feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.SOSO));
        result.put("badCount", feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.BAD));

        feedbackRepository.findByPlaceAndMember(place, member)
                .ifPresent(feedback -> result.put("myFeedback", feedback.getFeedbackType().name()));

        return result;
    }

    public void addOrUpdateFeedback(Long placeId, String nickname, FeedbackType feedbackType) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        PlaceFeedback feedback = feedbackRepository.findByPlaceAndMember(place, member)
                .orElse(new PlaceFeedback(place, member, feedbackType));

        feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long placeId, String nickname) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        feedbackRepository.findByPlaceAndMember(place, member)
                .ifPresent(feedbackRepository::delete);
    }
}
