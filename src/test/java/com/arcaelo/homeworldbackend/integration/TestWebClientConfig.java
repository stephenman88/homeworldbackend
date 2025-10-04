package com.arcaelo.homeworldbackend.integration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestWebClientConfig {
    @Bean
    public WebClient webClient(@Value("${gaApiUrl}") String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
