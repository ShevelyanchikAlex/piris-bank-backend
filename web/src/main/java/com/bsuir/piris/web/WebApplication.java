package com.bsuir.piris.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZoneOffset;
import java.util.TimeZone;

@EntityScan(basePackages = "com.bsuir.piris")
@EnableJpaRepositories(basePackages = "com.bsuir.piris")
@SpringBootApplication(scanBasePackages = "com.bsuir.piris")
public class WebApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
        SpringApplication.run(WebApplication.class, args);
    }
}
