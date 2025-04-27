package com.team4.place.repository;

import com.team4.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Query("SELECT p FROM Place p " +
            "WHERE p.latitude BETWEEN :minLat AND :maxLat " +
            "AND p.longitude BETWEEN :minLng AND :maxLng")
    List<Place> findPlacesInView(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                 @Param("minLng") double minLng, @Param("maxLng") double maxLng);
}