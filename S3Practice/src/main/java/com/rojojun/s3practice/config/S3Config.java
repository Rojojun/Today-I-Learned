package com.rojojun.s3practice.config;

import lombok.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    // S3의 accessKey
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    // S3의 secretKey
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
}
