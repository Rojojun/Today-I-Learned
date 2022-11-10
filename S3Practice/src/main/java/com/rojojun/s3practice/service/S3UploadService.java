package com.rojojun.s3practice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.rojojun.s3practice.etc.UploadUtils;
import com.rojojun.s3practice.model.PostImage;
import com.rojojun.s3practice.repository.PostImageRepository;
import org.springframework.beans.factory.annotation.*;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@PropertySource("classpath:s3.properties")
@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final AmazonS3Client amazonS3Client;
    private UploadUtils uploadUtils;
    private final PostImageRepository postImageRepository;

    @Value("{cloud.aws.s3.bucket")
    private String bucket;

    public PostImage uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
        PostImage postImage = new PostImage();

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("error : MultipartFile -> File convert fail"));

        HashMap<String, String> keyValue = uploadUtils.randomFileMap(multipartFile.getOriginalFilename(), dirName);
        String key = String.valueOf(keyValue.keySet());
        String value = String.valueOf(keyValue.values());

        postImageRepository.save(new PostImage(key, value));

        upload(uploadFile, dirName);

        return postImage;
    }

    public String upload(File uploadFile, String filePath) {
        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName); //S3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
        }
        else {
            System.out.println("File delete fail");
        }
    }

    // 로컬에 파일을 업로드 하는 메소드
    public Optional<File> convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(System.getProperty("user.dirName") + "/" + multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
                fileOutputStream.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}

