package com.team4.domain.place.entity;

import com.team4.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    private String name;

    private String address;

    private double latitude;

    private double longitude;

    @Column(name = "comments_cnt")
    private int commentsCnt;

    @ElementCollection
    @CollectionTable(name = "place_images", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> imgUrls = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Place(String name, String address, double latitude, double longitude, List<String> imgUrls) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.commentsCnt = 0;
        this.imgUrls = imgUrls;
    }

    public void commentsCntUp() {
        this.commentsCnt++;
    }
}
