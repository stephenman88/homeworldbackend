package com.arcaelo.homeworldbackend.integration;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.arcaelo.homeworldbackend.service.CardService;
import com.github.tomakehurst.wiremock.client.WireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes=TestWebClientConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = "gaApiUrl=http://localhost:9561")
public class CardControllerTests extends WireMockIntBase{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardService cardService;

    @BeforeEach
    void setupStub() throws IOException{
        wireMockServer.resetAll();
        String mockResponsePage1 = Files.readString(Paths.get("src\\test\\java\\com\\arcaelo\\homeworldbackend\\resources\\MockResponsePage1.json"));
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/cards/search"))
            .withQueryParam("page", WireMock.equalTo("1"))
            .willReturn(WireMock.aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(mockResponsePage1)
            .withStatus(200)
            )
        );

        String mockResponsePage2 = Files.readString(
            Paths.get("src\\test\\java\\com\\arcaelo\\homeworldbackend\\resources\\MockResponsePage2.json")
        );
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/cards/search"))
            .withQueryParam("page", WireMock.equalTo("2"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponsePage2)
                .withStatus(200)
            )
        );

        String mockResponsePage3 = Files.readString(
            Paths.get("src\\test\\java\\com\\arcaelo\\homeworldbackend\\resources\\MockResponsePageEmpty.json")
        );
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/cards/search"))
            .withQueryParam("page", WireMock.equalTo("3"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponsePage3)
                .withStatus(200))
        );

        cardService.pullAllCards();
    }

    @Test
    public void testPullAllCards(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/card"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.page_number").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("Apotheosis Rite"));
        }catch(Exception e){
            fail("CardControllerTests: testPullAllCards failed with exception: " + e.getMessage());
        }
        
    }

    @Test
    public void testPullSimpleCard(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?name=stonescale"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].uuid").value("sphwpjsznn"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?costReserveOperator==&costReserve=10"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].uuid").value("fcfxhkqda6"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?costMemoryOperator=>=&costMemory=2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(13))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[12].uuid").value("d7l6i5thdy"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?levelOperator=>&=level=1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(12))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].uuid").value("81gvGHkuVb"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[11].uuid").value("d7l6i5thdy"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?lifeOperator=<&life=16"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(15))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].uuid").value("fcfxhkqda6"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?powerOperator=<=&power=1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].uuid").value("782mm2tq5l"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?classes=cleric"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(14));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?effect=draw&page=3"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.page_data_count").value(8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].uuid").value("h973fdt8pt"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[7].uuid").value("vi1uyifw6s"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?flavor=wave"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].uuid").value("zq9ox7u6wz"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?effect=draw&classes=assassin"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(6))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[4].uuid").value("52u81v4c0z"));

        }catch(Exception e){
            fail("CardControllerTests: testPullSimpleCard failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void testPullComplexCard(){
        try{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?setPrefix=ReC-HVF"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].uuid").value("vi1uyifw6s"));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/card/search?themaGraceOperator=>&themaGrace=380"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_data_count").value(8))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[4].uuid").value("ltv5klryvf"));
        }catch(Exception e){
            fail("CardControllerTests: testPullComplexCard failed with exception: " + e.getMessage());
        }
    }
}