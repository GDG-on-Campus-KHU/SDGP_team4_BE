package com.team4.map.controller;

import com.team4.comment.dto.CommentDto;
import com.team4.map.service.MapService;
import com.team4.place.dto.PlaceDetailDto;
import com.team4.map.dto.MapPinDto;
import com.team4.place.dto.PlaceRequestDto;
import com.team4.place.service.PlaceService;
import com.team4.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;


    @GetMapping("/places/pin")
    public ResponseEntity<List<MapPinDto>> getPlacesInView(
            @RequestParam double minLat, @RequestParam double maxLat,
            @RequestParam double minLng, @RequestParam double maxLng) {

        List<MapPinDto> places = mapService.getMapInView(minLat, maxLat, minLng, maxLng);

        if (places.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(places);
    }

}