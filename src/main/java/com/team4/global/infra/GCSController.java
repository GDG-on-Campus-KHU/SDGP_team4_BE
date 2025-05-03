package com.team4.global.infra;

import com.team4.global.jwt.JwtService;
import com.team4.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Image")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bucket")
public class GCSController {

    private final ImgUploader imgUploader;
    @PostMapping(value = "/image", consumes = {"multipart/form-data"})
    public ResponseEntity<CommonResponse<ImgUrlDtos>> saveImage(
            @RequestBody MultipartFile[] images) throws IOException {
        String nickname = JwtService.getLoginMemberNickname();
        ImgUrlDtos imgUrlDtos = imgUploader.uploadImages(nickname, images);
        return ResponseEntity.ok(CommonResponse.ok(imgUrlDtos));
    }

}
