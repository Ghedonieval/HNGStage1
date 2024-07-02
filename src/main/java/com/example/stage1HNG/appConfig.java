package com.example.stage1HNG;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class appConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
