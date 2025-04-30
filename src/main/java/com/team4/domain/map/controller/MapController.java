package com.team4.domain.map.controller;

import com.team4.domain.map.service.MapService;
import com.team4.domain.map.dto.MapPinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 지도 API
 *
 * 1.
 *
 */

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