package com.team4.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class GCSConfig {

    @Value("${gcs.bucket.key}")
    private String gcsKey;

    @Bean
    public Storage storage() throws IOException {
        // Google Cloud Storage 인증 파일 읽기
        ClassPathResource resource = new ClassPathResource(gcsKey);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

        // Storage 객체 생성 및 반환
        return StorageOptions.newBuilder()
                .setProjectId("primal-asset-298817")
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
