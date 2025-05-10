package com.team4.map.service;

import com.team4.map.dto.MapPinDto;
import com.team4.place.entity.Place;
import com.team4.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public List<MapPinDto> getMapInView(double minLat, double maxLat, double minLng, double maxLng) {
        List<Place> places = placeRepository.findPlacesInView(minLat, maxLat, minLng, maxLng);
        return places.stream()
                .map(MapPinDto::fromEntity)
                .collect(Collectors.toList());
    }
}
