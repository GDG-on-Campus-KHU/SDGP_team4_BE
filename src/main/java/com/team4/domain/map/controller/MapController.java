package com.team4.domain.map.controller;

import com.team4.domain.map.service.MapService;
import com.team4.domain.map.dto.MapPinDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 지도 API
 *
 * 1. 지도에 있는 핀 목록 조회
 *
 */

@Tag(name = "Map", description = "지도 화면 관련 API")
@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/places/pin")
    @Operation(
            summary = "지도 화면 내 핀 목록 조회",
            description = """
                    ### 지도에 보이는 범위 내 장소 핀들을 조회합니다.
                    
                    - 클라이언트는 현재 화면의 지도 좌표 경계를 파라미터로 전송합니다.
                    - 서버는 해당 위경도 범위 안에 있는 장소들의 위치(latitude, longitude)와 댓글 수 (commentsCnt)를 반홥합니다.
                    
                    ### 요청 파라미터
                    - 'minLat' (double): 화면의 최소 위도
                    - 'maxLat' (double): 화면의 최대 위도
                    - 'minLng' (double): 화면의 최소 경도
                    - 'maxLng' (double): 화면의 최대 경도
                    """
    )
    public ResponseEntity<List<MapPinDto>> getPlacesInView(
            @RequestParam double minLat,
            @RequestParam double maxLat,
            @RequestParam double minLng,
            @RequestParam double maxLng) {

        List<MapPinDto> places = mapService.getMapInView(minLat, maxLat, minLng, maxLng);

        if (places.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(places);
    }

}