package com.rojojun.s3practice.controller;

import com.rojojun.s3practice.model.PostImage;
import com.rojojun.s3practice.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3UploadService s3Uploader;

    // 이미지 or 파일을 업로드 하는 메서드
    @PostMapping PostImage uploadImage(@RequestParam(value = "file") MultipartFile multipartFile) {
        // @RequestPram 에서 value를 정해줌 -> file이든 img든 커스텀 가능 통상적인 규정과 약속에 의거해 작성
        // required는 기본적으로 true 설정
        // 컨트롤러에서 받은 정보들을 가지고 S3UploadService 클래스의 지정된 메서드에서 작업 수행
        return s3Uploader.uploadImage(multipartFile, "image");
    }

    /*@PostMapping("/{userId}/image")
    public ResponseEntity<UserResponseDto> updateUserImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) { return new ResponseEntity(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }*/

    /*@PostMapping("/uploard")
    public PostImage uploadImg(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        return s3Uploader.uploadFiles(multipartFile, "post");
    }*/
}
