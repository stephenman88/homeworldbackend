package com.arcaelo.homeworldbackend.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.github.tomakehurst.wiremock.WireMockServer;

public abstract class WireMockIntBase {
    public static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock(){
        wireMockServer = new WireMockServer(9561);
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock(){
        if(wireMockServer != null){
            wireMockServer.stop();
        }
    }

    @DynamicPropertySource
    static void registerGaApiUrl(DynamicPropertyRegistry registry){
        registry.add("gaApiUrl", () -> "http://localhost:" + wireMockServer.port());
    }
}
