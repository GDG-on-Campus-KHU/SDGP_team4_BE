package com.team4.global.infra;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.team4.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImgUploader {

    @Value("${gcs.bucket.name}")
    private String bucketName;

    private final Storage storage;

    private final MemberRepository memberRepository;

    public ImgUrlDtos uploadImages(String nickname, MultipartFile[] multipartFiles) throws IOException {

        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            //각 이미지마다 고유한 UUID를 생성합니다.
            String uuid = UUID.randomUUID().toString();
            //업로드된 파일의 원본 파일명을 가져옵니다.
            String originalFilename = multipartFile.getOriginalFilename();
            //파일의 확장자를 추출합니다.
            String type = multipartFile.getContentType();
//            String type = FilenameUtils.getExtension(originalFilename);

            //storage를 사용하여 GCS에 새로운 Blob을 생성합니다.
            storage.create(BlobInfo.newBuilder(BlobId.of(bucketName, uuid))
                    .setContentType(type) //Blob의 컨텐츠 타입을 설정합니다.
                    .build(), multipartFile.getInputStream());
            //Blob을 생성하고 업로드할 파일의 입력 스트림을 전달합니다.

            //GCS에 업로드된 이미지의 공개 URL을 생성합니다.
            String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, uuid);
            uploadedUrls.add(fileUrl);
        }

        return new ImgUrlDtos(uploadedUrls);
    }
}
