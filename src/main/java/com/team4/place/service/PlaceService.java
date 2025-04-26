package com.team4.place.service;

import com.team4.place.dto.PlaceDetailDto;
import com.team4.map.dto.MapPinDto;
import com.team4.place.dto.PlaceRequestDto;
import com.team4.place.entity.Place;
import com.team4.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public PlaceDetailDto getPlaceDetail(int placeId) {
        return placeRepository.findById(placeId)
                .map(PlaceDetailDto::fromEntity)
                .orElse(null);
    }

    @Transactional
    public PlaceDetailDto addPlace(PlaceRequestDto request) {
        Place place = new Place(request.getName(), request.getAddress(), request.getLatitude(), request.getLongitude());
        Place savedPlace = placeRepository.save(place);
        return PlaceDetailDto.fromEntity(savedPlace);
    }
}
