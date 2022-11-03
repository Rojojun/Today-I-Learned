package com.rojojun.s3practice.controller;

import com.rojojun.s3practice.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3UploadService s3Uploader;

    @PostMapping("/{userId}/image")
    public ResponseEntity<UserResponseDto> updateUserImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) { return new ResponseEntity(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }