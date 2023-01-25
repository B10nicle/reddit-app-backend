package com.khilkoleg.redditapp;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import com.khilkoleg.redditapp.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;

@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class RedditAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedditAppApplication.class, args);
    }
}
