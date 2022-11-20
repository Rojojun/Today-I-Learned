package com.rojojun.s3practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class S3PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(S3PracticeApplication.class, args);
    }

}
