package com.team4.domain.post.api;

import com.team4.domain.post.dto.PostInfoDto;
import com.team4.domain.post.service.PostService;
import com.team4.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증/인가 API
 *
 * 1.
 */

@Tag(name = "Post")
@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @GetMapping
    public ResponseEntity<CommonResponse<PostInfoDto>> showPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(CommonResponse.ok(postService.showPosts(PageRequest.of(page, size))));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<PostInfoDto>> showPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(CommonResponse.ok(postService.showPosts(PageRequest.of(page, size))));
    }
}
