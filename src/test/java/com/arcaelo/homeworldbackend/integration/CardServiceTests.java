package com.arcaelo.homeworldbackend.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.arcaelo.homeworldbackend.repo.CardRepository;
import com.arcaelo.homeworldbackend.service.CardService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.arcaelo.homeworldbackend.model.Card;
import com.arcaelo.homeworldbackend.model.CardDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest(classes = TestWebClientConfig.class)
@TestPropertySource(properties = "gaApiUrl=http://localhost:9561")
public class CardServiceTests extends WireMockIntBase{
    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void setupStub(){
        wireMockServer.resetAll();
        String mockResponse = """
                {
  "data": [
    {
      "classes": [
        "WARRIOR"
      ],
      "cost_memory": 1,
      "cost_reserve": null,
      "created_at": "2024-10-11T12:00:00+00:00",
      "durability": 3,
      "editions": [
        {
          "card_id": "abcde12345",
          "circulationTemplates": [
            {
              "kind": "FOIL",
              "population": 100,
              "population_operator": "=",
              "printing": false,
              "created_at": "2024-10-11T12:00:00+00:00",
              "edition_id": "abcde12345",
              "foil": false,
              "uuid": "abcde12345",
              "variants": [],
              "name": "AMB NF C 2"
            }
          ],
          "circulations": [],
          "collaborators": [],
          "collector_number": "010",
          "configuration": "default",
          "created_at": "2024-10-11T12:00:00+00:00",
          "effect": null,
          "effect_html": null,
          "effect_raw": null,
          "flavor": "The centerpiece of the Imperial Armory.",
          "illustrator": "Dragonart",
          "image": "text",
          "last_update": "2024-10-11T12:00:00+00:00",
          "orientation": null,
          "other_orientations": [],
          "rarity": 1,
          "slug": "crescent-glaive-amb",
          "set": {
            "created_at": "2024-10-11T12:00:00+00:00",
            "id": "abcde12345",
            "language": "EN",
            "last_update": "2024-10-11T12:00:00+00:00",
            "name": "Mortal Ambition",
            "prefix": "AMB",
            "release_date": "2024-10-11T00:00:00"
          },
          "thema_charm_foil": null,
          "thema_ferocity_foil": null,
          "thema_foil": null,
          "thema_grace_foil": null,
          "thema_mystique_foil": null,
          "thema_valor_foil": null,
          "thema_charm_nonfoil": null,
          "thema_ferocity_nonfoil": null,
          "thema_grace_nonfoil": null,
          "thema_mystique_nonfoil": null,
          "thema_nonfoil": null,
          "thema_valor_nonfoil": null,
          "uuid": "abcde12345"
        }
      ],
      "elements": [
        "WATER"
      ],
      "effect": "[Class Bonus] [Level 2+] CARDNAME gets +1 [POWER]. *(Apply this effect only if your champion's class matches this card's class and only if your champion is level 2 or higher.)*",
      "effect_raw": "[Class Bonus] [Level 2+] Crescent Glaive gets +1 POWER. (Apply this effect only if your champion's class matches this card's class and only if your champion is level 2 or higher.)",
      "flavor": "Convalescing waves cascade through the soul, revitalizing the body and mind from the depths.",
      "last_update": "2024-10-11T12:00:00+00:00",
      "legality": null,
      "level": null,
      "life": null,
      "name": "Crescent Glaive",
      "power": 1,
      "referenced_by": [],
      "references": [],
      "result_editions": [
        {
          "card_id": "abcde12345",
          "circulationTemplates": [
            {
              "kind": "FOIL",
              "population": 100,
              "population_operator": "=",
              "printing": false,
              "created_at": "2024-10-11T12:00:00+00:00",
              "edition_id": "abcde12345",
              "foil": false,
              "uuid": "abcde12345",
              "variants": [],
              "name": "AMB NF C 2"
            }
          ],
          "circulations": [],
          "collaborators": [],
          "collector_number": "010",
          "configuration": "default",
          "created_at": "2024-10-11T12:00:00+00:00",
          "effect": null,
          "effect_html": null,
          "effect_raw": null,
          "flavor": "The centerpiece of the Imperial Armory.",
          "illustrator": "Dragonart",
          "image": "text",
          "last_update": "2024-10-11T12:00:00+00:00",
          "orientation": null,
          "other_orientations": [],
          "rarity": 1,
          "slug": "crescent-glaive-amb",
          "set": {
            "created_at": "2024-10-11T12:00:00+00:00",
            "id": "abcde12345",
            "language": "EN",
            "last_update": "2024-10-11T12:00:00+00:00",
            "name": "Mortal Ambition",
            "prefix": "AMB",
            "release_date": "2024-10-11T00:00:00"
          },
          "thema_charm_foil": null,
          "thema_ferocity_foil": null,
          "thema_foil": null,
          "thema_grace_foil": null,
          "thema_mystique_foil": null,
          "thema_valor_foil": null,
          "thema_charm_nonfoil": null,
          "thema_ferocity_nonfoil": null,
          "thema_grace_nonfoil": null,
          "thema_mystique_nonfoil": null,
          "thema_nonfoil": null,
          "thema_valor_nonfoil": null,
          "uuid": "abcde12345"
        }
      ],
      "rule": [],
      "speed": null,
      "slug": "crescent-glaive",
      "subtypes": [
        "WARRIOR",
        "POLEARM"
      ],
      "types": [
        "REGALIA",
        "WEAPON"
      ],
      "uuid": "abcde12345"
    }
  ],
  "has_more": false,
  "order": "ASC",
  "page": 1,
  "page_size": 30,
  "paginated_cards_count": 1,
  "sort": "collector_number",
  "total_cards": 1,
  "total_pages": 1
}
                """;
                wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/card/search"))
                    .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)
                        .withStatus(200)
                    )
                );  
    }

    @Test
    void pullCards_fetchesFromApiAndSavesToDb(){
        List<CardDTO> result = cardService.pullCards();

        assertNotNull(result);
        assertEquals(1, result.size());
        
        List<Card> cards = cardRepository.findAll();
        assertEquals(1, cards.size());
        assertTrue(cards.stream().anyMatch(c -> "abcde12345".equals(c.getUUID())));
        
        CardDTO dto = cardService.getCardById("abcde12345").orElseThrow();
        assertTrue(dto.getClasses().contains("WARRIOR"));
        assertEquals(1, dto.getCostMemory());
        assertTrue(dto.getCostReserve() == null);
        assertEquals(3, dto.getDurability());
        assertTrue(dto.getElements().contains("WATER"));
        assertEquals(
            "[Class Bonus] [Level 2+] CARDNAME gets +1 [POWER]. *(Apply this effect only if your champion's class matches this card's class and only if your champion is level 2 or higher.)*", 
            dto.getEffect());
        assertEquals(
            "[Class Bonus] [Level 2+] Crescent Glaive gets +1 POWER. (Apply this effect only if your champion's class matches this card's class and only if your champion is level 2 or higher.)",
            dto.getEffectRaw()
        );
        assertEquals(
            "Convalescing waves cascade through the soul, revitalizing the body and mind from the depths.",
            dto.getFlavor()
        );
        assertNull(dto.getLegality());
        assertNull(dto.getLevel());
        assertNull(dto.getLife());
        assertEquals("Crescent Glaive", dto.getName());
        assertEquals(1, dto.getPower());
        assertEquals(0, dto.getReferencedBy().size());
        assertEquals(0, dto.getReferences().size());
        assertEquals(0, dto.getRule().size());
        assertTrue(dto.getSpeed() == null);
        assertEquals("crescent-glaive", dto.getSlug());
        assertTrue(dto.getSubtypes().containsAll(List.of("WARRIOR", "POLEARM")));
        assertTrue(dto.getTypes().containsAll(List.of("REGALIA", "WEAPON")));
    }
}
