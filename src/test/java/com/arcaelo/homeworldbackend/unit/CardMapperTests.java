package com.arcaelo.homeworldbackend.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.arcaelo.homeworldbackend.model.Card;
import com.arcaelo.homeworldbackend.model.CardDTO;
import com.arcaelo.homeworldbackend.model.CardMapper;
import com.arcaelo.homeworldbackend.model.CardMapperImpl;
import com.arcaelo.homeworldbackend.model.Edition;
import com.arcaelo.homeworldbackend.repo.EditionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

//@SpringBootTest for integration testing
@JsonTest
@TestPropertySource("classpath:env.properties")
@Import(CardMapperImpl.class)
public class CardMapperTests {
    @Autowired
    private CardMapper cardMapper;

    @MockitoBean
    private EditionRepository editionRepository;

    @Test
    void testToDTO(){
        Edition edition1 = new Edition();
        edition1.setUUID("ed1");
        Edition edition2 = new Edition();
        edition2.setUUID("ed2");

        Card card = new Card();
        card.setUUID("card1");
        card.setName("Test Card 1");
        card.setEditions(List.of(edition1, edition2));

        CardDTO dto = cardMapper.toDTO(card);

        assertNotNull(dto);
        assertEquals(card.getName(), "Test Card 1");
        assertEquals(card.getUUID(), "card1");
        assertEquals(List.of("ed1", "ed2"), dto.getEditionIds());
    }

    @Test
    void testToEntity(){
        CardDTO dto = new CardDTO();
        dto.setUUID("card 1");
        dto.setName("Test Card 1");
        
        Edition edition = new Edition();
        edition.setUUID("edition 1");
        dto.setEditionIds(List.of("edition 1"));

        when(editionRepository.findById("edition 1")).thenReturn(Optional.of(edition));

        Card card = cardMapper.toEntity(dto);
        assertNotNull(card);
        assertEquals(card.getUUID(), "card 1");
        assertEquals(card.getName(), "Test Card 1");
        assertEquals(card.getEditions().size(), 1);
        assertEquals(card.getEditions().get(0).getUUID(), "edition 1");
    }
}
