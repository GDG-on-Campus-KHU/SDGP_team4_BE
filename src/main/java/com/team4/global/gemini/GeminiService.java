package com.team4.global.gemini;

import com.team4.global.gemini.dto.GeminiRequest;
import com.team4.global.gemini.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String getContents(String prompt) {

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        GeminiRequest request = new GeminiRequest(prompt);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);

        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();

        return message;
    }
}
