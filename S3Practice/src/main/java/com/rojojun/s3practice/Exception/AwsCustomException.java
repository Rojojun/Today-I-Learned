package com.rojojun.s3practice.Exception;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AwsCustomException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public AwsCustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
