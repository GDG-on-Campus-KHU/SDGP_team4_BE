package com.team4.feedback.repository;


import com.team4.feedback.entity.FeedbackType;
import com.team4.feedback.entity.PlaceFeedback;
import com.team4.member.entity.Member;
import com.team4.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceFeedbackRepository extends JpaRepository<PlaceFeedback, Integer> {

    Optional<PlaceFeedback> findByPlaceAndMember(Place place, Member member);

    List<PlaceFeedback> findAllByPlace(Place place);

    int countByPlaceAndFeedbackType(Place place, FeedbackType feedbackType);
}
