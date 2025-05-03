package com.team4.domain.post.dto;

import java.util.List;

public record PostUpdateDto (
        String title,
        String description,
        List<String> imgUrls
) {
}
