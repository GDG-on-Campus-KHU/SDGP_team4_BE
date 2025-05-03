package com.team4.global.infra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ImgUrlDtos {
    @Schema(description = "이미지 URL", example = "https:///1629780000000.jpg")
    private List<String> imageUrl;
}
