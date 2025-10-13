package com.arcaelo.homeworldbackend.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.arcaelo.homeworldbackend.service.CardService;
import com.github.tomakehurst.wiremock.client.WireMock;

import jakarta.transaction.Transactional;

import com.arcaelo.homeworldbackend.model.CardDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.CardSetResponseDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.OrientationDTO;
import com.arcaelo.homeworldbackend.model.CardResponseDTO.EditionResponseDTO.OrientationDTO.InnerEditionResponseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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
        assertEquals(31, result.size());
        
        assertApotheosisRite();
        assertSpiritOfSlime();
    }

    @Test
    void pullAllCards_fetchesFromApiAndSavesToDb(){
        List<CardDTO> result = cardService.pullAllCards();

        assertNotNull(result);
        assertEquals(61, result.size());
        
        assertApotheosisRite();
        assertSpiritOfSlime();

        assertInsigniaOfCorhazi();
    }

    @Test
    void getAllCards(){
        List<CardDTO> dtos = cardService.getAllCards();
        assertNotNull(dtos);
        assertEquals(61, dtos.size());
    }

    @Test
    @Transactional
    void getAllCardResponses(){
        List<CardResponseDTO> dtos = cardService.getAllCardResponses();
        assertNotNull(dtos);
        assertEquals(61, dtos.size());

        assertSpiritOfWind(dtos);
        assertAzuriteFatestone(dtos);
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

    void assertSpiritOfWind(List<CardResponseDTO> dtos){
        //Test Spirit of Wind
        CardResponseDTO spiritOfWindDTO = null;
        for(CardResponseDTO currentDto : dtos){
            if(currentDto.getUUID().equals("pNiyaGlIe7")){
                spiritOfWindDTO = currentDto;
            }
        }

        assertNotNull(spiritOfWindDTO);
        assertTrue(spiritOfWindDTO.getClasses().containsAll(Set.of("SPIRIT")));
        assertEquals(0, spiritOfWindDTO.getCostMemory());
        assertTrue(spiritOfWindDTO.getCostReserve() == null);
        assertTrue(spiritOfWindDTO.getDurability() == null);
        assertTrue(spiritOfWindDTO.getElements().contains("WIND"));
        assertEquals(
            "**On Enter:** Draw seven cards.", 
            spiritOfWindDTO.getEffect());
        assertEquals(
            "On Enter: Draw seven cards.",
            spiritOfWindDTO.getEffectRaw()
        );
        assertEquals("Turbulent zephyrs swirl around the soul, inspiring fleet-footed dreams and expedited efficiency.",
            spiritOfWindDTO.getFlavor()
        );
        assertNull(spiritOfWindDTO.getLegality());
        assertEquals(0, spiritOfWindDTO.getLevel());
        assertEquals(15, spiritOfWindDTO.getLife());
        assertEquals("Spirit of Wind", spiritOfWindDTO.getName());
        assertNull(spiritOfWindDTO.getPower());
        assertEquals(0, spiritOfWindDTO.getReferencedBy().size());
        assertEquals(0, spiritOfWindDTO.getReferences().size());
        assertEquals(0, spiritOfWindDTO.getRule().size());
        assertNull(spiritOfWindDTO.getSpeed());
        assertEquals("spirit-of-wind", spiritOfWindDTO.getSlug());
        assertTrue(spiritOfWindDTO.getSubtypes().containsAll(List.of("SPIRIT")));
        assertTrue(spiritOfWindDTO.getTypes().containsAll(List.of("CHAMPION")));

        List<EditionResponseDTO> editionResponses = spiritOfWindDTO.getEditionResponseDTOs();
        assertEquals(12, editionResponses.size());

        EditionResponseDTO ambEditionDTO = null;
        for(EditionResponseDTO editionResponse : editionResponses){
            if(editionResponse.getUUID().equals("xv5in4t3wc")){
                ambEditionDTO = editionResponse;
            }
        }
        assertNotNull(ambEditionDTO);
        assertEquals("pNiyaGlIe7", ambEditionDTO.getCardId());
        assertTrue(ambEditionDTO.getCollaborators().size() == 0);
        assertEquals("003", ambEditionDTO.getCollectorNumber());
        assertEquals("default", ambEditionDTO.getConfiguration());
        assertNull(ambEditionDTO.getEffect());
        assertNull(ambEditionDTO.getEffectRaw());
        assertNull(ambEditionDTO.getEffectHtml());
        assertEquals("Auspicious fortune and good tidings follow the silver tiger on its fleet-footed journey through seasons.", ambEditionDTO.getFlavor());
        assertEquals("木叶", ambEditionDTO.getIllustrator());
        assertEquals("/cards/images/spirit-of-wind-amb.jpg", ambEditionDTO.getImage());
        assertNull(ambEditionDTO.getOrientation());
        assertEquals(0, ambEditionDTO.getOtherOrientationCard().size());
        assertEquals(1, ambEditionDTO.getRarity());
        assertEquals("spirit-of-wind-amb", ambEditionDTO.getSlug());
        assertNull(ambEditionDTO.getThemaCharmFoil());
        assertNull(ambEditionDTO.getThemaFerocityFoil());
        assertNull(ambEditionDTO.getThemaFoil());
        assertNull(ambEditionDTO.getThemaGraceFoil());
        assertNull(ambEditionDTO.getThemaMystiqueFoil());
        assertNull(ambEditionDTO.getThemaValorFoil());
        assertEquals(9, ambEditionDTO.getThemaCharmNonfoil());
        assertEquals(13, ambEditionDTO.getThemaFerocityNonfoil());
        assertEquals(17, ambEditionDTO.getThemaGraceNonfoil());
        assertEquals(13, ambEditionDTO.getThemaMystiqueNonfoil());
        assertEquals(65, ambEditionDTO.getThemaNonfoil());
        assertEquals(13, ambEditionDTO.getThemaValorNonfoil());

        assertNotNull(ambEditionDTO.getCardSetResponseDTO());
        CardSetResponseDTO ambCardsetDTO = ambEditionDTO.getCardSetResponseDTO();
        assertEquals("7pk8b8vm9v", ambCardsetDTO.getId());
        assertEquals("EN", ambCardsetDTO.getLanguage());
        assertEquals("2024-10-11T00:00", ambCardsetDTO.getReleaseDate().toString());
        assertEquals("Mortal Ambition", ambCardsetDTO.getName());
        assertEquals("AMB", ambCardsetDTO.getPrefix());
        assertEquals("2025-01-18T17:40:17.152+00:00", ambCardsetDTO.getLastUpdate());
    }

    void assertAzuriteFatestone(List<CardResponseDTO> dtos){
        CardResponseDTO fatestoneDTO = null;
        for(CardResponseDTO currentDto : dtos){
            if(currentDto.getUUID().equals("6ce5rzrjd9")){
                fatestoneDTO = currentDto;
            }
        }

        assertNotNull(fatestoneDTO);
        assertTrue(fatestoneDTO.getClasses().containsAll(Set.of("TAMER")));
        assertEquals(10, fatestoneDTO.getCostMemory());
        assertTrue(fatestoneDTO.getCostReserve() == null);
        assertTrue(fatestoneDTO.getDurability() == null);
        assertTrue(fatestoneDTO.getElements().contains("ARCANE"));
        assertEquals(
            "**Immortality**, **Spellshroud**\n" + //
                                "\n" + //
                                "[Guo Jia Bonus] At the beginning of your end phase, you may banish a card at random from your memory. If you do, draw a card.\n" + //
                                "\n" + //
                                "[Guo Jia Bonus] Whenever you banish a card from your memory, put a **quest** counter on your champion. \n" + //
                                "\n" + //
                                "[REST]: You may remove ten **quest** counters from your champion. If you do, wake up and transform CARDNAME.", 
            fatestoneDTO.getEffect());
        assertEquals(
            "Immortality, Spellshroud\n" + //
                                "\n" + //
                                "[Guo Jia Bonus] At the beginning of your end phase, you may banish a card at random from your memory. If you do, draw a card.\n" + //
                                "\n" + //
                                "[Guo Jia Bonus] Whenever you banish a card from your memory, put a quest counter on your champion. \n" + //
                                "\n" + //
                                "REST: You may remove ten quest counters from your champion. If you do, wake up and transform Fabled Azurite Fatestone.",
            fatestoneDTO.getEffectRaw()
        );
        assertNull(
            fatestoneDTO.getFlavor()
        );
        assertNull(fatestoneDTO.getLegality());
        assertNull(fatestoneDTO.getLevel());
        assertNull(fatestoneDTO.getLife());
        assertEquals("Fabled Azurite Fatestone", fatestoneDTO.getName());
        assertNull(fatestoneDTO.getPower());
        assertEquals(0, fatestoneDTO.getReferencedBy().size());
        assertEquals(1, fatestoneDTO.getReferences().size());
        assertEquals("GENERATE", fatestoneDTO.getReferences().get(0).get("kind"));
        assertEquals("Arcane Blast", fatestoneDTO.getReferences().get(0).get("name"));
        assertEquals("arcane-blast", fatestoneDTO.getReferences().get(0).get("slug"));
        assertEquals("TO", fatestoneDTO.getReferences().get(0).get("direction"));
        assertEquals(3, fatestoneDTO.getRule().size());
        for (HashMap<String, String> rule : fatestoneDTO.getRule()){
            assertEquals("2025-03-02", rule.get("date_added"));
            if(rule.get("description").equals("The quest counter ability only triggers if the controller is the one banishing a card from their memory. It doesn't trigger if a different player banishes a card from it. For example, the quest counter ability will not trigger from an opponent resolving a Chilling Touch against a player that controls Fabled Azurite Fatestone. ") ||
                rule.get("description").equals("If a transformed double-faced object would enter the graveyard from the field, it goes to the default location that the front would go. In this case, a regalia goes to its owner's banishment.")){
                assertEquals("", rule.get("title"));
            }else if(rule.get("description").equals("Immortal -> Immortality")){
                assertEquals("ERRATA", rule.get("title"));
            }else{
                fail("rule description does not match any of the 3 rules");
            }
        }
        assertNull(fatestoneDTO.getSpeed());
        assertEquals("fabled-azurite-fatestone", fatestoneDTO.getSlug());
        assertTrue(fatestoneDTO.getSubtypes().containsAll(List.of("TAMER", "FATESTONE")));
        assertTrue(fatestoneDTO.getTypes().containsAll(List.of("REGALIA", "ITEM")));

        List<EditionResponseDTO> editionResponses = fatestoneDTO.getEditionResponseDTOs();
        assertEquals(3, editionResponses.size());

        EditionResponseDTO ambEditionDTO = null;
        for(EditionResponseDTO editionResponse : editionResponses){
            if(editionResponse.getUUID().equals("6ce5rzrjd9")){
                ambEditionDTO = editionResponse;
            }
        }
        assertNotNull(ambEditionDTO);
        assertEquals("6ce5rzrjd9", ambEditionDTO.getCardId());
        assertTrue(ambEditionDTO.getCollaborators().size() == 0);
        assertEquals("027", ambEditionDTO.getCollectorNumber());
        assertEquals("flip", ambEditionDTO.getConfiguration());
        assertEquals("**%Immortal%Immortality%**, **Spellshroud**\n" + //
                        "\n" + //
                        "[Guo Jia Bonus] At the beginning of your end phase, you may banish a card at random from your memory. If you do, draw a card.\n" + //
                        "\n" + //
                        "[Guo Jia Bonus] Whenever you banish a card from your memory, put a **quest** counter on your champion. \n" + //
                        "\n" + //
                        "[REST]: You may remove ten **quest** counters from your champion. If you do, wake up and transform CARDNAME.", ambEditionDTO.getEffect());
        assertEquals("Immortality, Spellshroud\n" + //
                        "\n" + //
                        "[Guo Jia Bonus] At the beginning of your end phase, you may banish a card at random from your memory. If you do, draw a card.\n" + //
                        "\n" + //
                        "[Guo Jia Bonus] Whenever you banish a card from your memory, put a quest counter on your champion. \n" + //
                        "\n" + //
                        "REST: You may remove ten quest counters from your champion. If you do, wake up and transform Fabled Azurite Fatestone.", ambEditionDTO.getEffectRaw());
        assertEquals("<span class=\"effect__block\"><span class=\"effect__label\"><span class=\"effect__strikethrough\">Immortal</span> <span class=\"effect__correction\">Immortality</span></span>, <span class=\"effect__label\">Spellshroud</span></span><span class=\"effect__block\"><span class=\"effect__bubble\">Guo Jia Bonus</span>  At the beginning of your end phase, you may banish a card at random from your memory. If you do, draw a card.</span><span class=\"effect__block\"><span class=\"effect__bubble\">Guo Jia Bonus</span>  Whenever you banish a card from your memory, put a <span class=\"effect__label\">quest</span> counter on your champion. </span><span class=\"effect__icon effect__icon--rest\"></span>: You may remove ten <span class=\"effect__label\">quest</span> counters from your champion. If you do, wake up and transform Fabled Azurite Fatestone.", ambEditionDTO.getEffectHtml());
        assertNull(ambEditionDTO.getFlavor());
        assertEquals("Parhelion Studio Co.", ambEditionDTO.getIllustrator());
        assertEquals("/cards/images/fabled-azurite-fatestone-hvn1e-csr.jpg", ambEditionDTO.getImage());
        assertEquals("front", ambEditionDTO.getOrientation());
        assertEquals(7, ambEditionDTO.getRarity());
        assertEquals("fabled-azurite-fatestone-hvn1e-csr", ambEditionDTO.getSlug());
        assertNull(ambEditionDTO.getThemaCharmNonfoil());
        assertNull(ambEditionDTO.getThemaFerocityNonfoil());
        assertNull(ambEditionDTO.getThemaNonfoil());
        assertNull(ambEditionDTO.getThemaGraceNonfoil());
        assertNull(ambEditionDTO.getThemaMystiqueNonfoil());
        assertNull(ambEditionDTO.getThemaValorNonfoil());
        assertEquals(345, ambEditionDTO.getThemaCharmFoil());
        assertEquals(393, ambEditionDTO.getThemaFerocityFoil());
        assertEquals(364, ambEditionDTO.getThemaGraceFoil());
        assertEquals(391, ambEditionDTO.getThemaMystiqueFoil());
        assertEquals(1860, ambEditionDTO.getThemaFoil());
        assertEquals(367, ambEditionDTO.getThemaValorFoil());

        assertEquals(1, ambEditionDTO.getOtherOrientationCard().size());
        OrientationDTO orientationDTO = ambEditionDTO.getOtherOrientationCard().get(0);
        assertTrue(orientationDTO.getClasses().contains("TAMER"));
        assertNull(orientationDTO.getCostMemory());
        assertEquals(10, orientationDTO.getCostReserve());
        assertNull(orientationDTO.getDurability());
        assertEquals("**Spellshroud**, **Taunt**\n\n[Guo Jia Bonus] **On Attack:** Choose one— \n • Generate an Arcane Blast card and banish it. As long as it's banished, you may activate it, ignoring its elemental requirements.\n • **Empower X**+2, where **X** is the amount of arcane element cards in your banishment.",
            orientationDTO.getEffect()
        );
        assertEquals(
            "Spellshroud, Taunt\n\n[Guo Jia Bonus] On Attack: Choose one— \n • Generate an Arcane Blast card and banish it. As long as it's banished, you may activate it, ignoring its elemental requirements.\n • Empower X+2, where X is the amount of arcane element cards in your banishment.", 
            orientationDTO.getEffectRaw());
        assertTrue(orientationDTO.getElements().contains("ARCANE"));
        assertNull(orientationDTO.getFlavor());
        assertNull(orientationDTO.getLevel());
        assertEquals(12, orientationDTO.getLife());
        assertEquals("Seiryuu, Azure Dragon", orientationDTO.getName());
        assertEquals(4, orientationDTO.getPower());
        assertEquals("seiryuu-azure-dragon", orientationDTO.getSlug());
        assertNull(orientationDTO.getSpeed());
        assertTrue(orientationDTO.getSubtypes().containsAll(Set.of("TAMER", "BEAST", "SHENJU", "FATEBOUND", "DRAGON")));
        assertTrue(orientationDTO.getTypes().contains("ALLY"));

        InnerEditionResponseDTO innerEditionDTO = orientationDTO.getEditionResponseDTO();
        assertNotNull(innerEditionDTO);
        assertEquals("fcfxhkqda6", innerEditionDTO.getUUID());
        assertEquals("seiryuu-azure-dragon-hvn1e-csr", innerEditionDTO.getSlug());
        assertEquals("/cards/images/seiryuu-azure-dragon-hvn1e-csr.jpg", innerEditionDTO.getImage());
        assertNull(innerEditionDTO.getEffect());
        assertNull(innerEditionDTO.getFlavor());
        assertEquals(7, innerEditionDTO.getRarity());
        assertEquals("fcfxhkqda6", innerEditionDTO.getCardId());
        assertNull(innerEditionDTO.getEffectRaw());
        assertEquals("Doyngu", innerEditionDTO.getIllustrator());
        assertEquals("back", innerEditionDTO.getOrientation());
        assertEquals(0, innerEditionDTO.getCollaborators().size());
        assertEquals("flip", innerEditionDTO.getConfiguration());
        assertEquals("027", innerEditionDTO.getCollectorNumber());
        assertNull(innerEditionDTO.getEffectHtml());
        assertNotNull(innerEditionDTO.getCardSet());
        CardSetResponseDTO innerCardSetDTO = innerEditionDTO.getCardSet();
        assertEquals("28fb5edf8a", innerCardSetDTO.getId());
        assertEquals("Abyssal Heaven First Edition", innerCardSetDTO.getName());
        assertEquals("HVN 1st", innerCardSetDTO.getPrefix());
        assertEquals("EN", innerCardSetDTO.getLanguage());
        assertEquals("2025-03-04T21:56:32.906+00:00", innerCardSetDTO.getLastUpdate());
        assertEquals("2025-03-07T00:00", innerCardSetDTO.getReleaseDate().toString());

        assertNotNull(ambEditionDTO.getCardSetResponseDTO());
        CardSetResponseDTO ambCardsetDTO = ambEditionDTO.getCardSetResponseDTO();
        assertEquals("28fb5edf8a", ambCardsetDTO.getId());
        assertEquals("EN", ambCardsetDTO.getLanguage());
        assertEquals("2025-03-07T00:00", ambCardsetDTO.getReleaseDate().toString());
        assertEquals("Abyssal Heaven First Edition", ambCardsetDTO.getName());
        assertEquals("HVN 1st", ambCardsetDTO.getPrefix());
        assertEquals("2025-03-04T21:56:32.906+00:00", ambCardsetDTO.getLastUpdate());
    }
}
