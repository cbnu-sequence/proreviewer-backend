package com.sequence.proreviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProreviewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProreviewerApplication.class, args);
    }
}
