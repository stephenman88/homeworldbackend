package com.arcaelo.homeworldbackend.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.arcaelo.homeworldbackend.service.CardService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.arcaelo.homeworldbackend.model.CardDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = TestWebClientConfig.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "gaApiUrl=http://localhost:9561")
public class CardServiceTests extends WireMockIntBase{
    @Autowired
    private CardService cardService;

    @BeforeEach
    void setupStub() throws IOException{
        wireMockServer.resetAll();
        String mockResponsePage1 = Files.readString(Paths.get("src\\test\\java\\com\\arcaelo\\homeworldbackend\\resources\\MockResponsePage1.json"));
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/card/search"))
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
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/card/search"))
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
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/card/search"))
            .withQueryParam("page", WireMock.equalTo("3"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponsePage3)
                .withStatus(200))
        );
    }

    @Test
    void pullOnePage_fetchesFromApiAndSavesToDb(){
        List<CardDTO> result = cardService.pullCards(1);

        assertNotNull(result);
        assertEquals(30, result.size());
        
        assertApotheosisRite();
        assertSpiritOfSlime();
    }

    @Test
    void pullAllCards_fetchesFromApiAndSavesToDb(){
        List<CardDTO> result = cardService.pullAllCards();

        assertNotNull(result);
        assertEquals(60, result.size());
        
        assertApotheosisRite();
        assertSpiritOfSlime();

        assertInsigniaOfCorhazi();
    }

    void assertApotheosisRite(){
        CardDTO dto = cardService.getCardById("df594Qoszn").orElseThrow();
        assertTrue(dto.getClasses().contains("WARRIOR"));
        assertEquals(0, dto.getCostMemory());
        assertTrue(dto.getCostReserve() == null);
        assertTrue(dto.getDurability() == null);
        assertTrue(dto.getElements().contains("NORM"));
        assertEquals(
            "**Divine Relic**\n\n**Banish CARDNAME:** Your champion becomes an Ascendant in addition to its other types. Draw a card.", 
            dto.getEffect());
        assertEquals(
            "Divine Relic\n\nBanish Apotheosis Rite: Your champion becomes an Ascendant in addition to its other types. Draw a card.",
            dto.getEffectRaw()
        );
        assertEquals(
            "\"Only one will rise above.\"",
            dto.getFlavor()
        );
        assertNull(dto.getLegality());
        assertNull(dto.getLevel());
        assertNull(dto.getLife());
        assertEquals("Apotheosis Rite", dto.getName());
        assertTrue(dto.getPower() == null);
        assertEquals(0, dto.getReferencedBy().size());
        assertEquals(0, dto.getReferences().size());
        assertEquals(0, dto.getRule().size());
        assertTrue(dto.getSpeed() == null);
        assertEquals("apotheosis-rite", dto.getSlug());
        assertTrue(dto.getSubtypes().containsAll(List.of("WARRIOR", "RING")));
        assertTrue(dto.getTypes().containsAll(List.of("REGALIA", "ITEM")));
    }

    void assertSpiritOfSlime(){
        CardDTO dto = cardService.getCardById("0xp4xq07vv").orElseThrow();
        assertTrue(dto.getClasses().contains("SPIRIT"));
        assertEquals(0, dto.getCostMemory());
        assertTrue(dto.getCostReserve() == null);
        assertTrue(dto.getDurability() == null);
        assertTrue(dto.getElements().contains("NORM"));
        assertEquals(
            "**On Enter:**Draw seven cards.\n\n**Inherited Effect:** Ignore the elemental requirements of basic element Slime cards you activate.", 
            dto.getEffect());
        assertEquals(
            "On Enter:Draw seven cards.\n\nInherited Effect: Ignore the elemental requirements of basic element Slime cards you activate.",
            dto.getEffectRaw()
        );
        assertEquals(
            "An aura of amiability radiates from the soul, but for some reason, it feels a bit sticky.",
            dto.getFlavor()
        );
        assertNull(dto.getLegality());
        assertEquals(0, dto.getLevel());
        assertEquals(15, dto.getLife());
        assertEquals("Spirit of Slime", dto.getName());
        assertTrue(dto.getPower() == null);
        assertEquals(0, dto.getReferencedBy().size());
        assertEquals(0, dto.getReferences().size());
        assertEquals(0, dto.getRule().size());
        assertTrue(dto.getSpeed() == null);
        assertEquals("spirit-of-slime", dto.getSlug());
        assertTrue(dto.getSubtypes().containsAll(List.of("SPIRIT")));
        assertTrue(dto.getTypes().containsAll(List.of("CHAMPION")));
    }

    void assertInsigniaOfCorhazi(){
        CardDTO dto = cardService.getCardById("52u81v4c0z").orElseThrow();
        assertTrue(dto.getClasses().contains("ASSASSIN"));
        assertEquals(0, dto.getCostMemory());
        assertTrue(dto.getCostReserve() == null);
        assertTrue(dto.getDurability() == null);
        assertTrue(dto.getElements().contains("LUXEM"));
        assertEquals(
            "**(3), [REST]:** Put a **preparation** counter on your champion.\n\n[Class Bonus] Whenever you activate a **prepared** card while your **influence** is six or less, draw a card into your memory.", 
            dto.getEffect());
        assertEquals(
            "(3), REST: Put a preparation counter on your champion.\n\n[Class Bonus] Whenever you activate a prepared card while your influence is six or less, draw a card into your memory.",
            dto.getEffectRaw()
        );
        assertEquals(
            "A reward for agents of brilliant skill and service.",
            dto.getFlavor()
        );
        assertNull(dto.getLegality());
        assertNull(dto.getLevel());
        assertNull(dto.getLife());
        assertEquals("Insignia of the Corhazi", dto.getName());
        assertNull(dto.getPower());
        assertEquals(0, dto.getReferencedBy().size());
        assertEquals(0, dto.getReferences().size());
        assertEquals(0, dto.getRule().size());
        assertNull(dto.getSpeed());
        assertEquals("insignia-of-the-corhazi", dto.getSlug());
        assertTrue(dto.getSubtypes().containsAll(List.of("ASSASSIN", "ARTIFACT")));
        assertTrue(dto.getTypes().containsAll(List.of("REGALIA", "ITEM")));
    }

    void assertMerlinKingslayer(){
        CardDTO dto = cardService.getCardById("rz1bqry41l").orElseThrow();
        assertTrue(dto.getClasses().containsAll(Set.of("MAGE", "WARRIOR")));
        assertEquals(3, dto.getCostMemory());
        assertTrue(dto.getCostReserve() == null);
        assertTrue(dto.getDurability() == null);
        assertTrue(dto.getElements().contains("CRUX"));
        assertEquals(
            "**Merlin Lineage**\n\nAt the beginning of your recollection phase, put a **level** counter on CARDNAME. Then, if there's an even amount of **level** counters on CARDNAME, draw a card and CARDNAME's attacks get +2 [POWER] until end of turn.", 
            dto.getEffect());
        assertEquals(
            "Merlin Lineage\n\nAt the beginning of your recollection phase, put a level counter on Merlin. Then, if there's an even amount of level counters on Merlin, draw a card and Merlin's attacks get +2 POWER until end of turn.",
            dto.getEffectRaw()
        );
        assertNull(
            dto.getFlavor()
        );
        assertNull(dto.getLegality());
        assertEquals(3, dto.getLevel());
        assertEquals(28, dto.getLife());
        assertEquals("Merlin, Kingslayer", dto.getName());
        assertNull(dto.getPower());
        assertEquals(0, dto.getReferencedBy().size());
        assertEquals(0, dto.getReferences().size());
        assertEquals(0, dto.getRule().size());
        assertNull(dto.getSpeed());
        assertEquals("merlin-kingslayer", dto.getSlug());
        assertTrue(dto.getSubtypes().containsAll(List.of("MAGE", "WARRIOR", "HUMAN")));
        assertTrue(dto.getTypes().containsAll(List.of("CHAMPION")));
    }
}
