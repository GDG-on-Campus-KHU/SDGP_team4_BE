package com.team4.domain.place.entity;

import com.team4.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
@Getter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private int placeId;

    private String name;

    private String address;

    private double latitude;

    private double longitude;

    @Column(name = "comments_cnt")
    private int commentsCnt;

    @Column(name = "best_cnt")
    private int bestCnt;

    @Column(name = "good_cnt")
    private int goodCnt;

    @Column(name = "soso_cnt")
    private int sosoCnt;

    @Column(name = "bad_cnt")
    private int badCnt;


    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    public Place(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.commentsCnt = 0;
        this.bestCnt = 0;
        this.goodCnt = 0;
        this.sosoCnt = 0;
        this.badCnt = 0;
    }
}
