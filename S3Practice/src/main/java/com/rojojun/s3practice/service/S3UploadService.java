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

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
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
   /* private UploadUtils uploadUtils;
    private final PostImageRepository postImageRepository;*/
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    /*
     * 컨트롤러에서 받은 upload 관련 메서드 수행
     * */
    public PostImage uploadImage(MultipartFile multipartFile, String image) {
        // 1. 경로, 파일 이름을 저장하기 위해 PostImage 객체 생성
        PostImage postImage = new PostImage();
        // 2. MultipartFile 타입으로 되어 있는 multipartFile변수 정보를 불러옴
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        // 3. 메서드 convertMultiPartFileToFile 역직렬화 되어있는 MultipartFile을 다시 File로 직렬화
        File file = conver
    }
    // 역직렬화된 MultiPartFile -> 직렬화 File
    // NPE 안전한 Optional 객체 사용하여, 예외처리
    public Optional<File> convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        // MultiPartFile로 받아온 파일들을 file로 정렬화
        File file = new File(System.getProperty("user.dirName") + "/" + multipartFile.getOriginalFilename());
        // File 클래스 createNewFile() 메서드로 빈 파일을 생성
        if (file.createNewFile()) {
        // FileOutputStream 클래스 사용, File 객체가 가리키는 파일을 쓰기 위한 객체 생성, 파일이 존재하는 경우 덮어쓰기 진행
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
        // 입력받은 내용을 파일 내용으로 기록한다.
                fileOutputStream.write(multipartFile.getBytes());
            }
        // Optional을 사용하여 null이 아닌 객체를 받음, 객체에 값이 없을 경우 NPE 발생
            return Optional.of(file);
        }
        // 비어있는 경우에 empty 메소드를 받아 exception 던진다.
        return Optional.empty();
    }
    public boolean chkValidation(String ext) {
        return true;
    }

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

