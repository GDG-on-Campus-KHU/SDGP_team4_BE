package com.team4.domain.map.service;

import com.team4.domain.feedback.entity.FeedbackType;
import com.team4.domain.feedback.repository.PlaceFeedbackRepository;
import com.team4.domain.map.dto.MapPinDto;
import com.team4.domain.place.entity.Place;
import com.team4.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {

    private final PlaceRepository placeRepository;
    private final PlaceFeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public List<MapPinDto> getMapInView(double minLat, double maxLat, double minLng, double maxLng) {
        List<Place> places = placeRepository.findPlacesInView(minLat, maxLat, minLng, maxLng);

        return places.stream()
                .map(place -> {
                    // 각 feedback 유형별 카운트 조회
                    int best   = feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.BEST);
                    int good   = feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.GOOD);
                    int soso   = feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.SOSO);
                    int bad    = feedbackRepository.countByPlaceAndFeedbackType(place, FeedbackType.BAD);

                    return MapPinDto.fromEntity(place, best, good, soso, bad);
                })
                .collect(Collectors.toList());
    }
}
