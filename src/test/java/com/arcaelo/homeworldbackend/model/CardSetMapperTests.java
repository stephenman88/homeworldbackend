package com.arcaelo.homeworldbackend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.arcaelo.homeworldbackend.repo.EditionRepository;

@JsonTest
@TestPropertySource("classpath:env.properties")
@Import(CardSetMapperImpl.class)
public class CardSetMapperTests {
    @Autowired
    private CardSetMapper cardSetMapper;

    @MockitoBean
    private EditionRepository editionRepository;

    @Test
    void testToDTO(){
        CardSet cardSet = new CardSet();
        cardSet.setId("DOA");
        cardSet.setName("Dawn of Ashes");
        
        Edition edition = new Edition();
        edition.setUUID("1st ed");
        Edition ed2 = new Edition();
        ed2.setUUID("alter");

        cardSet.setCardEditions(List.of(edition, ed2));

        CardSetDTO dto = cardSetMapper.toDTO(cardSet);
        assertNotNull(dto);
        assertEquals(dto.getId(), "DOA");
        assertEquals(dto.getName(), "Dawn of Ashes");
        assertEquals(dto.getCardEditionIds(), Set.of("1st ed", "alter"));
    }

    @Test
    void testToEntity(){
        CardSetDTO dto = new CardSetDTO();
        dto.setId("set1");
        dto.setName("Dawn of Ashes");
        dto.setCardEditionsIds(Set.of("1st ed", "alter"));

        Edition ed1 = new Edition();
        ed1.setUUID("1st ed");
        Edition ed2 = new Edition();
        ed2.setUUID("alter");
        when(editionRepository.findById("1st ed")).thenReturn(Optional.of(ed1));
        when(editionRepository.findById("alter")).thenReturn(Optional.of(ed2));

        CardSet set = cardSetMapper.toEntity(dto);
        assertNotNull(set);
        assertEquals(set.getId(), "set1");
        assertEquals(set.getName(), "Dawn of Ashes");
        assertEquals(set.getCardEditions().get(0).getUUID(), "1st ed");
        assertEquals(set.getCardEditions().get(1).getUUID(), "alter");
    }
}
