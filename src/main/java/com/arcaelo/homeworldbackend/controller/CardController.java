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
        @RequestParam(required = false) List<String> classes, //done
        @RequestParam(required = false) String costMemoryOperator, //done
        @RequestParam(required = false) Integer costMemory, //done
        @RequestParam(required = false) String costReserveOperator, //done
        @RequestParam(required = false) Integer costReserve, //done
        @RequestParam(required = false) String durabilityOperator, //done
        @RequestParam(required = false) Integer durability, //done
        @RequestParam(required = false) String effect, //done
        @RequestParam(required = false) List<Integer> rarity, //done
        @RequestParam(required = false) List<String> setPrefix, //done
        @RequestParam(required = false) String themaCharmOperator, //done
        @RequestParam(required = false) Integer themaCharm, //done
        @RequestParam(required = false) String themaFerocityOperator, //done
        @RequestParam(required = false) Integer themaFerocity, //done
        @RequestParam(required = false) String themaGraceOperator, //done
        @RequestParam(required = false) Integer themaGrace, //done
        @RequestParam(required = false) String themaMystiqueOperator, //done
        @RequestParam(required = false) Integer themaMystique, //done
        @RequestParam(required = false) String themaSumOperator, //done
        @RequestParam(required = false) Integer themaSum, //done
        @RequestParam(required = false) String themaValorOperator, //done
        @RequestParam(required = false) Integer themaValor, //done
        @RequestParam(required = false) List<String> element, //done
        @RequestParam(required = false) String flavor, //done
        @RequestParam(required = false) String legalityFormat, //not working yet
        @RequestParam(required = false) String limitOperator, //not working yet
        @RequestParam(required = false) Integer limit, //not working yet
        @RequestParam(required = false) String levelOperator, //done
        @RequestParam(required = false) Integer level, //done
        @RequestParam(required = false) String lifeOperator, //done
        @RequestParam(required = false) Integer life, //done
        @RequestParam(required = false) String name, //done
        @RequestParam(required = false) String powerOperator, //done
        @RequestParam(required = false) Integer power, //done
        @RequestParam(required = false) Boolean speed, //done
        @RequestParam(required = false) String slug, //done
        @RequestParam(required = false) List<String> subtype, //done
        @RequestParam(required = false) List<String> type, //done
        @RequestParam(required = false) String id,
        @RequestParam(required = false) Integer page){ //done
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