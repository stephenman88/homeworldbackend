package com.arcaelo.homeworldbackend.unit;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.arcaelo.homeworldbackend.model.EditionMapper;
import com.arcaelo.homeworldbackend.model.EditionMapperImpl;
import com.arcaelo.homeworldbackend.model.Card;
import com.arcaelo.homeworldbackend.model.CardSet;
import com.arcaelo.homeworldbackend.model.Edition;
import com.arcaelo.homeworldbackend.model.EditionDTO;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(EditionMapperImpl.class)
@TestPropertySource("classpath:env.properties")
public class EditionMapperTests {
    private final EditionMapper editionMapper = new EditionMapperImpl();

    @Test
    void testToDTO(){
        Edition edition = new Edition();
        edition.setUUID("test ed 1");
        edition.setEffect("test regular effect");
        edition.setEffectHtml("text html effect");
        edition.setCollaborators(Set.of("Hori", "Hanh Chu"));
        
        Card c1 = new Card();
        c1.setUUID("card1");
        c1.setName("ghost of pendragon");
        edition.setCard(c1);

        Card o1 = new Card();
        o1.setUUID("orientation 1");
        o1.setName("front");
        Card o2 = new Card();
        o2.setUUID("orientation 2");
        o2.setName("back");
        edition.setOtherOrientation(Set.of(o1, o2));

        CardSet cs = new CardSet();
        cs.setId("cardset1");
        cs.setName("DOA");
        edition.setCardSet(cs);

        EditionDTO dto = editionMapper.toDTO(edition);
        assertNotNull(dto);
        assertEquals("test ed 1", dto.getUUID());
        assertTrue(dto.getCollaborators().containsAll(Set.of("Hori", "Hanh Chu")));
        assertEquals("card1", dto.getCardId());
        assertTrue(dto.getOtherOrientationCardId().contains("orientation 2"));
        assertEquals("cardset1", dto.getCardSetId());
    }
}
