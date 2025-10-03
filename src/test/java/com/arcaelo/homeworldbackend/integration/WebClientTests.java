package com.arcaelo.homeworldbackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@SpringBootTest(classes = TestWebClientConfig.class)
public class WebClientTests extends WireMockIntBase{
    @Autowired
private WebClient.Builder webClientBuilder;

private WebClient webClient;

@Value("${gaApiUrl}")
private String gaApiUrl;

@BeforeEach
void setup() {
    webClient = webClientBuilder.baseUrl(gaApiUrl).build();
}

@Test
void checkWebClientBaseUrl() {
    System.out.println(webClient); // should show base URL = http://localhost:9561
    
}
}
