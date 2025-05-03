package com.team4.domain.place.service;

import com.team4.domain.place.dto.PlaceDetailDto;
import com.team4.domain.map.dto.MapPinDto;
import com.team4.domain.place.dto.PlaceRequestDto;
import com.team4.domain.place.entity.Place;
import com.team4.domain.place.repository.PlaceRepository;
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
    public PlaceDetailDto getPlaceDetail(Long placeId) {
        return placeRepository.findById(placeId)
                .map(PlaceDetailDto::fromEntity)
                .orElse(null);
    }

    @Transactional
    public PlaceDetailDto addPlace(PlaceRequestDto request) {
        Place place = new Place(request.getName(), request.getAddress(), request.getLatitude(), request.getLongitude(), request.getImgUrls());
        Place savedPlace = placeRepository.save(place);
        return PlaceDetailDto.fromEntity(savedPlace);
    }
}
