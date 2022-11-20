package com.rojojun.s3practice.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK(HttpStatus.OK, 200, "true"),
    FILE_CONVERT_ERROR(HttpStatus.NO_CONTENT, 500, "알 수 없는 오류로 인해, 업로드에 실패하였습니다."),
    FILE_CANNOT_DELETE(HttpStatus.BAD_REQUEST, 500, "알 수 없는 오류로 인해, 파일삭제에 실패하였습니다.");

    private final HttpStatus status;
    private final int errorCode;
    private final String errorMessage;
}
