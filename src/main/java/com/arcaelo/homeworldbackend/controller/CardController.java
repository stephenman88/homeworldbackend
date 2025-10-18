package com.arcaelo.homeworldbackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.arcaelo.homeworldbackend.model.CardResponseDTO;
import com.arcaelo.homeworldbackend.service.CardService;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;
    private static final Integer pageSize = 15;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping
    public CardResponseShell getAllCards(@RequestParam(required = false) Integer page){
        if(page == null){page = 1;}
        Integer endIndex = page * pageSize;
        Integer startIndex = endIndex - pageSize;
        List<CardResponseDTO> cardDTOs = cardService.getAllCardResponses();
        if(cardDTOs.size() <= startIndex){
            return new CardResponseShell(
                List.of(),
                0,
                0,
                1,
                1,
                false
            );
        }

        List<CardResponseDTO> cardResponseDTOs = cardDTOs.subList(startIndex, endIndex <= cardDTOs.size() ? endIndex : cardDTOs.size());
        CardResponseShell response = new CardResponseShell(
            cardResponseDTOs,
            cardDTOs.size(),
            cardResponseDTOs.size(),
            page,
            cardDTOs.size()/15 + 1,
            endIndex < cardDTOs.size()
        );
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseShell> getCardById(@PathVariable String id){
        try{
            CardResponseDTO responseDTO = cardService.getCardResponseById(id).orElseThrow();
            CardResponseShell response = new CardResponseShell(
                List.of(responseDTO),
                1,
                1,
                1,
                1,
                false
            );
            return ResponseEntity.ok(response);
        }catch(Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/search")
    public CardResponseShell getCardsByParameter(
        @RequestParam(required = false) List<String> classes,
        @RequestParam(required = false) String costMemoryOperator,
        @RequestParam(required = false) Integer costMemory,
        @RequestParam(required = false) String costReserveOperator,
        @RequestParam(required = false) Integer costReserve,
        @RequestParam(required = false) String durabilityOperator,
        @RequestParam(required = false) Integer durability,
        @RequestParam(required = false) String effect,
        @RequestParam(required = false) List<String> rarity, //not working yet
        @RequestParam(required = false) List<String> setPrefix, //not working yet
        @RequestParam(required = false) String themaCharmOperator,
        @RequestParam(required = false) Integer themaCharm,
        @RequestParam(required = false) String themaFerocityOperator,
        @RequestParam(required = false) Integer themaFerocity,
        @RequestParam(required = false) String themaGraceOperator,
        @RequestParam(required = false) Integer themaGrace,
        @RequestParam(required = false) String themaMystiqueOperator,
        @RequestParam(required = false) Integer themaMystique,
        @RequestParam(required = false) String themaSumOperator,
        @RequestParam(required = false) Integer themaSum,
        @RequestParam(required = false) String themaValorOperator,
        @RequestParam(required = false) Integer themaValor,
        @RequestParam(required = false) List<String> element,
        @RequestParam(required = false) String flavor,
        @RequestParam(required = false) String legalityFormat, //not working yet
        @RequestParam(required = false) String limitOperator, //not working yet
        @RequestParam(required = false) Integer limit,
        @RequestParam(required = false) String levelOperator,
        @RequestParam(required = false) Integer level,
        @RequestParam(required = false) String lifeOperator,
        @RequestParam(required = false) Integer life,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String powerOperator,
        @RequestParam(required = false) Integer power,
        @RequestParam(required = false) Boolean speed,
        @RequestParam(required = false) String slug,
        @RequestParam(required = false) List<String> subtype,
        @RequestParam(required = false) List<String> type,
        @RequestParam(required = false) String id,
        @RequestParam(required = false) Integer page){
            List<CardResponseDTO> cardDTOs = cardService.getCardResponsesByParameter(
                classes,
                costMemoryOperator,
                costMemory,
                costReserveOperator,
                costReserve,
                durabilityOperator,
                durability,
                effect,
                rarity,
                setPrefix,
                themaCharmOperator,
                themaCharm,
                themaFerocityOperator,
                themaFerocity,
                themaGraceOperator,
                themaGrace,
                themaMystiqueOperator,
                themaMystique,
                themaSumOperator,
                themaSum,
                themaValorOperator,
                themaValor,
                element,
                flavor,
                legalityFormat,
                limitOperator,
                limit,
                levelOperator,
                level,
                lifeOperator,
                life,
                name,
                powerOperator,
                power,
                speed,
                slug,
                subtype,
                type,
                id
            );

            if(page == null){page = 1;}
            Integer endIndex = page * pageSize;
            Integer startIndex = endIndex - pageSize;
            if(cardDTOs.size() <= startIndex){
                return new CardResponseShell(
                    List.of(),
                    0,
                    0,
                    1,
                    1,
                    false
                );
            }


        List<CardResponseDTO> cardResponseDTOs = cardDTOs.subList(startIndex, endIndex <= cardDTOs.size() ? endIndex : cardDTOs.size());
        CardResponseShell response = new CardResponseShell(
            cardResponseDTOs,
            cardDTOs.size(),
            cardResponseDTOs.size(),
            page,
            cardDTOs.size()/15 + 1,
            endIndex < cardDTOs.size()
        );
        return response;
    }

    private class CardResponseShell{
        @JsonProperty("data")
        private List<CardResponseDTO> cardResponseDTOs;
        @JsonProperty("page_data_count")
        private Integer dataCount;
        @JsonProperty("total_data_count")
        private Integer totalCount;
        @JsonProperty("page_number")
        private Integer pageNumber;
        @JsonProperty("has_next")
        private Boolean hasNext;
        @JsonProperty("total_pages")
        private Integer totalPages;

        public CardResponseShell(
            List<CardResponseDTO> cardResponseDTOs,
            Integer totalCount,
            Integer dataCount,
            Integer pageNumber,
            Integer totalPages,
            Boolean hasNext
        ){
            this.dataCount = dataCount;
            this.totalCount = totalCount;
            this.pageNumber = pageNumber;
            this.cardResponseDTOs = cardResponseDTOs;
            this.totalPages = totalPages;
            this.hasNext = hasNext;
        }
    }
}