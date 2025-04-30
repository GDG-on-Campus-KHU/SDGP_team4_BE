package com.team4.domain.feedback.entity;

import com.team4.domain.member.entity.Member;
import com.team4.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback",
        uniqueConstraints = @UniqueConstraint(columnNames = {"place_id", "member_id"}))
@Getter
@NoArgsConstructor
public class PlaceFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type", nullable = false)
    private FeedbackType feedbackType;

    public PlaceFeedback(Place place, Member member, FeedbackType feedbackType) {
        this.place = place;
        this.member = member;
        this.feedbackType = feedbackType;
    }
}
