package com.team4.comment.entity;

import com.team4.member.entity.Member;
import com.team4.place.entity.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "local")
    private boolean isLocal;

    private String comment;

    public Comment(Place place, Member member, boolean isLocal, String comment) {
        this.place = place;
        this.member = member;
        this.isLocal = isLocal;
        this.comment = comment;
    }
}
