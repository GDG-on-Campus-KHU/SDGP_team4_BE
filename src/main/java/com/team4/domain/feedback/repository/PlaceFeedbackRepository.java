package com.team4.domain.feedback.repository;


import com.team4.domain.feedback.entity.FeedbackType;
import com.team4.domain.feedback.entity.PlaceFeedback;
import com.team4.domain.member.domain.Member;
import com.team4.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceFeedbackRepository extends JpaRepository<PlaceFeedback, Long> {

    Optional<PlaceFeedback> findByPlaceAndMember(Place place, Member member);

    List<PlaceFeedback> findAllByPlace(Place place);

    int countByPlaceAndFeedbackType(Place place, FeedbackType feedbackType);
}
