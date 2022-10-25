package com.rojojun.jpainter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaInterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaInterApplication.class, args);
    }

}
