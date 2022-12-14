package com.rojojun.s3practice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.rojojun.s3practice.Exception.AwsCustomException;
import com.rojojun.s3practice.Exception.ErrorCode;
import com.rojojun.s3practice.model.PostImage;
import com.rojojun.s3practice.repository.PostImageRepository;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@PropertySource("classpath:s3.properties")
@Service
@RequiredArgsConstructor
@Slf4j
public class S3UploadService {
    private final AmazonS3Client amazonS3Client;
    //private UploadUtils uploadUtils;
    private final PostImageRepository postImageRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    /*
     * 컨트롤러에서 받은 upload 관련 메서드 수행
     * */
    public PostImage uploadImage(MultipartFile multipartFile, String dirName) throws IOException {
        // 1. 경로, 파일 이름을 저장하기 위해 PostImage 객체 생성
        PostImage postImage;
        // 2. MultipartFile 타입으로 되어 있는 multipartFile변수 정보를 불러옴
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        // 3. 메서드 convertMultiPartFileToFile 역직렬화 되어있는 MultipartFile을 다시 File로 직렬화
        //    이후 convertMultiPartFileToFile 메서드에서 파일에 대한 모든 정보를 저장하기 위해 처리
        File file = convertMultiPartFileToFile(multipartFile).orElseThrow(() -> new AwsCustomException(ErrorCode.FILE_CONVERT_ERROR));
        // 4. 랜덤한 파일 이름을 생성하는 메소드를 통해 key, S3에 저장된 업로드 경로를 통해 path를 Key, Value 형태로 저장함
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        // 5. 만들어진 파일을 현재 스토리지에서 삭제
        // 파일 이동 절차 : 원본 -> 소스 / 빌드 스토리지 -> S3 (중간 매게체인 소스/빌드스토리지에는 있어야할 필요가 없음)
        removeFile(file);
        // 6. PostImage의 객체 형태로 반환하여 Key, Value값을 entity로 반환
        postImage = new PostImage(path, key);
        postImageRepository.save(postImage);
        return postImage;
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
    private void removeFile(File file){
        try {
            // 파일 삭제 메서드 -> 직렬화된 파일을 지우는 내장 함수
            file.delete();
        } catch (Exception e){
            new AwsCustomException(ErrorCode.FILE_CONVERT_ERROR);
        }
    }
    public boolean chkValidation(String ext) {
        return true;
    }
    // 원본의 파일을 받아 UUID의 랜덤값 + 원본 파일이름으로 되어 있는 형태로 같은 이름이 중복되지 않도록 하는 메소드
    public String randomFileName(File file, String dirName){
        return dirName + "/" + UUID.randomUUID() + file.getName();
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
    // 여기서 부터는 동작 구동하는지 확인하기위해 만든 시험 코드
    public void test(MultipartFile multipartFile, String dirName) throws IOException{
        // 역직렬화된 파일을 직렬화시켜서 변환 (자바 내부 Byte 형태로 변환하여 업로드 진행)
        /*File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));*/
        File file = convertLocalFile(multipartFile).orElseThrow(() -> new AwsCustomException(ErrorCode.FILE_CONVERT_ERROR));
        uploadFromLocal(file, dirName);
    }

    private void uploadFromLocal(File uploadFile, String dirName) {
        // Local -> 소스가 있는 directory
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = uploadFromSouceDir(uploadFile, fileName);
        // 소스가 있는 directory 파일에 있는 파일 제거 -> 불필요하게 저장되어 있기에 방지
        removeTestFile(uploadFile);
    }

    private String uploadFromSouceDir(File uploadFile, String fileName) {
        // 소스가 있는 dir -> S3
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeTestFile(File target) {
        if (target.delete()) {
            log.info("file delete success");
            return;
        }
        log.warn("not deleted");
    }

    private Optional<File> convertLocalFile(MultipartFile file) throws IOException {
        System.out.println("hi");
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        // File convertFile = new File(System.getProperty("user.dirName") + "/" + file.getOriginalFilename());

       /* if (!convertFile.getParentFile().exists())
            convertFile.getParentFile().mkdirs();*/
        // 직렬화된 파일을 로컬에서 소스 경로로 복제
        if (convertFile.createNewFile()) {
            // 복제된 파일을 생성화
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)){
                // 바이트 단위로 구조화 된 파일을 읽어서 저장
                fileOutputStream.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}

