package com.arcaelo.homeworldbackend.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CardResponseDTO {
    private Set<String> classes;
    private Integer costMemory;
    private Integer costReserve;
    private Integer durability;
    private Set<String> elements;
    private String effect;
    private String effectRaw;
    private String flavor;
    private LocalDateTime lastUpdate;
    private HashMap<String, HashMap<String, Integer>> legality;
    private Integer level;
    private Integer life;
    private String name;
    private Integer power;
    private List<HashMap<String, String>> referencedBy;
    private List<HashMap<String, String>> references;
    private Set<HashMap<String, String>> rule;
    private Boolean speed;
    private String slug;
    private Set<String> subtypes;
    private Set<String> types;
    private String uuid;
    private List<EditionResponseDTO> editionResponseDTOs;

    public static class EditionResponseDTO{
        private String cardId;
        private Set<String> collaborators;
        private String collectorNumber;
        private String configuration;
        private String effect;
        private String effectHtml;
        private String effectRaw;
        private String flavor;
        private String illustrator;
        private String image;
        private String orientation;
        private List<OrientationDTO> otherOrientation;
        private Integer rarity;
        private String slug;
        private CardSetResponseDTO cardSet;
        private Integer themaCharmFoil;
        private Integer themaFerocityFoil;
        private Integer themaFoil;
        private Integer themaGraceFoil;
        private Integer themaMystiqueFoil;
        private Integer themaValorFoil;
        private Integer themaCharmNonfoil;
        private Integer themaFerocityNonfoil;
        private Integer themaNonfoil;
        private Integer themaGraceNonfoil;
        private Integer themaMystiqueNonfoil;
        private Integer themaValorNonfoil;
        private String uuid;

        public static class OrientationDTO{
            private Set<String> classes;
            private Integer costMemory;
            private Integer costReserve;
            private Integer durability;
            private Set<String> elements;
            private String effect;
            private String effectRaw;
            private String flavor;
            private String editionId;
            private InnerEditionResponseDTO editionResponseDTO;
            private Integer level;
            private Integer life;
            private String name;
            private Integer power;
            private Boolean speed;
            private String slug;
            private Set<String> subtypes;
            private Set<String> types;
            private String uuid;

            public static class InnerEditionResponseDTO{
                private String cardId;
                private Set<String> collaborators;
                private String collectorNumber;
                private String configuration;
                private String effect;
                private String effectHtml;
                private String effectRaw;
                private String flavor;
                private String illustrator;
                private String image;
                private String orientation;
                private Integer rarity;
                private String slug;
                private CardSetResponseDTO cardSet;
                private Integer themaCharmFoil;
                private Integer themaFerocityFoil;
                private Integer themaFoil;
                private Integer themaGraceFoil;
                private Integer themaMystiqueFoil;
                private Integer themaValorFoil;
                private Integer themaCharmNonfoil;
                private Integer themaFerocityNonfoil;
                private Integer themaNonfoil;
                private Integer themaGraceNonfoil;
                private Integer themaMystiqueNonfoil;
                private Integer themaValorNonfoil;
                private String uuid;

                public String getCardId(){return cardId;}
                public void setCardId(String cardId){this.cardId = cardId;}
                public Set<String> getCollaborators(){return collaborators;}
                public void setCollaborators(Set<String> collaborators){this.collaborators = collaborators;}
                public String getCollectorNumber(){return collectorNumber;}
                public void setCollectorNumber(String collectorNumber){this.collectorNumber = collectorNumber;}
                public String getConfiguration(){return configuration;}
                public void setConfiguration(String configuration){this.configuration = configuration;}
                public String getEffect(){return effect;}
                public void setEffect(String effect){this.effect = effect;}
                public String getEffectHtml(){return effectHtml;}
                public void setEffectHtml(String effectHtml){this.effectHtml = effectHtml;}
                public String getEffectRaw(){return effectRaw;}
                public void setEffectRaw(String effectRaw){this.effectRaw = effectRaw;}
                public String getFlavor(){return flavor;}
                public void setFlavor(String flavor){this.flavor = flavor;}
                public String getIllustrator(){return illustrator;}
                public void setIllustrator(String illustrator){this.illustrator = illustrator;}
                public String getImage(){return image;}
                public void setImage(String image){this.image = image;}
                public String getOrientation(){return orientation;}
                public void setOrientation(String orientation){this.orientation = orientation;}
                public Integer getRarity(){return rarity;}
                public void setRarity(Integer rarity){this.rarity = rarity;}
                public String getSlug(){return slug;}
                public void setSlug(String slug){this.slug = slug;}
                public CardSetResponseDTO getCardSet(){return cardSet;}
                public void setCardSet(CardSetResponseDTO cardSet){this.cardSet = cardSet;}
                public Integer getThemaCharmFoil(){return themaCharmFoil;}
                public void setThemaCharmFoil(Integer themaCharmFoil){this.themaCharmFoil = themaCharmFoil;}
                public Integer getThemaFerocityFoil(){return themaFerocityFoil;}
                public void setThemaFerocityFoil(Integer themaFerocityFoil){this.themaFerocityFoil = themaFerocityFoil;}
                public Integer getThemaFoil(){return this.themaFoil;}
                public void setThemaFoil(Integer themaFoil){this.themaFoil = themaFoil;}
                public Integer getThemaGraceFoil(){return themaGraceFoil;}
                public void setThemaGraceFoil(Integer themaGraceFoil){this.themaGraceFoil = themaGraceFoil;}
                public Integer getThemaMystiqueFoil(){return themaMystiqueFoil;}
                public void setThemaMystiqueFoil(Integer themaMystiqueFoil){this.themaMystiqueFoil = themaMystiqueFoil;}
                public Integer getThemaValorFoil(){return themaValorFoil;}
                public void setThemaValorFoil(Integer themaValorFoil){this.themaValorFoil = themaValorFoil;}
                public Integer getThemaCharmNonfoil(){return themaCharmNonfoil;}
                public void setThemaCharmNonfoil(Integer themaCharmNonfoil){this.themaCharmNonfoil = themaCharmNonfoil;}
                public Integer getThemaFerocityNonfoil(){return themaFerocityNonfoil;}
                public void setThemaFerocityNonfoil(Integer themaFerocityNonfoil){this.themaFerocityNonfoil = themaFerocityNonfoil;}
                public Integer getThemaNonfoil(){return this.themaNonfoil;}
                public void setThemaNonfoil(Integer themaNonfoil){this.themaNonfoil = themaNonfoil;}
                public Integer getThemaGraceNonfoil(){return themaGraceNonfoil;}
                public void setThemaGraceNonfoil(Integer themaGraceNonfoil){this.themaGraceNonfoil = themaGraceNonfoil;}
                public Integer getThemaMystiqueNonfoil(){return themaMystiqueNonfoil;}
                public void setThemaMystiqueNonfoil(Integer themaMystiqueNonfoil){this.themaMystiqueNonfoil = themaMystiqueNonfoil;}
                public Integer getThemaValorNonfoil(){return themaValorNonfoil;}
                public void setThemaValorNonfoil(Integer themaValorNonfoil){this.themaValorNonfoil = themaValorNonfoil;}
                public String getUUID(){return uuid;}
                public void setUUID(String uuid){this.uuid = uuid;}
            }

            public Set<String> getClasses(){return classes;}
            public void setClasses(Set<String> classes){this.classes = classes;}
            public Integer getCostMemory(){return costMemory;}
            public void setCostMemory(Integer costMemory){this.costMemory = costMemory;}
            public Integer getCostReserve(){return costReserve;}
            public void setCostReserve(Integer costReserve){this.costReserve = costReserve;}
            public Integer getDurability(){return durability;}
            public void setDurability(Integer durability){this.durability = durability;}
            public String getEditionId(){return editionId;}
            public void setEditionId(String editionId){this.editionId = editionId;}
            public InnerEditionResponseDTO getEditionResponseDTO(){return editionResponseDTO;}
            public void setEditionResponseDTO(InnerEditionResponseDTO editionResponseDTO){this.editionResponseDTO = editionResponseDTO;}
            public Set<String> getElements(){return elements;}
            public void setElements(Set<String> elements){this.elements = elements;}
            public String getEffect(){return effect;}
            public void setEffect(String effect){this.effect = effect;}
            public String getEffectRaw(){return effectRaw;}
            public void setEffectRaw(String effectRaw){this.effectRaw = effectRaw;}
            public String getFlavor(){return flavor;}
            public void setFlavor(String flavor){this.flavor = flavor;}
            public Integer getLevel(){return level;}
            public void setLevel(Integer level){this.level = level;}
            public Integer getLife(){return life;}
            public void setLife(Integer life){this.life = life;}
            public String getName(){return name;}
            public void setName(String name){this.name = name;}
            public Integer getPower(){return power;}
            public void setPower(Integer power){this.power = power;}
            public Boolean getSpeed(){return speed;}
            public void setSpeed(Boolean speed){this.speed = speed;}
            public String getSlug(){return slug;}
            public void setSlug(String slug){this.slug = slug;}
            public Set<String> getSubtypes(){return subtypes;}
            public void setSubtypes(Set<String> subtypes){this.subtypes = subtypes;}
            public Set<String> getTypes(){return types;}
            public void setTypes(Set<String> types){this.types = types;}
            public String getUUID(){return uuid;}
            public void setUUID(String uuid){this.uuid = uuid;}
        }

        public static class CardSetResponseDTO{
            private String id;
            private String language;
            private String lastUpdate;
            private String name;
            private String prefix;
            private LocalDateTime releaseDate;

            public String getId(){return id;}
            public void setId(String id){this.id = id;}
            public String getLanguage(){return language;}
            public void setLanguage(String language){this.language = language;}
            public String getLastUpdate(){return lastUpdate;}
            public void setLastUpdate(String lastUpdate){this.lastUpdate = lastUpdate;}
            public String getName(){return name;}
            public void setName(String name){this.name = name;}
            public String getPrefix(){return prefix;}
            public void setPrefix(String prefix){this.prefix = prefix;}
            public LocalDateTime getReleaseDate(){return releaseDate;}
            public void setReleaseDate(LocalDateTime releaseDate){this.releaseDate = releaseDate;}
        }

        public String getCardId(){return cardId;}
        public void setCardId(String cardId){this.cardId = cardId;}
        public Set<String> getCollaborators(){return collaborators;}
        public void setCollaborators(Set<String> collaborators){this.collaborators = collaborators;}
        public String getCollectorNumber(){return collectorNumber;}
        public void setCollectorNumber(String collectorNumber){this.collectorNumber = collectorNumber;}
        public String getConfiguration(){return configuration;}
        public void setConfiguration(String configuration){this.configuration = configuration;}
        public String getEffect(){return effect;}
        public void setEffect(String effect){this.effect = effect;}
        public String getEffectHtml(){return effectHtml;}
        public void setEffectHtml(String effectHtml){this.effectHtml = effectHtml;}
        public String getEffectRaw(){return effectRaw;}
        public void setEffectRaw(String effectRaw){this.effectRaw = effectRaw;}
        public String getFlavor(){return flavor;}
        public void setFlavor(String flavor){this.flavor = flavor;}
        public String getIllustrator(){return illustrator;}
        public void setIllustrator(String illustrator){this.illustrator = illustrator;}
        public String getImage(){return image;}
        public void setImage(String image){this.image = image;}
        public String getOrientation(){return orientation;}
        public void setOrientation(String orientation){this.orientation = orientation;}
        public List<OrientationDTO> getOtherOrientationCard(){return otherOrientation;}
        public void setOtherOrientationCard(List<OrientationDTO> otherOrientation){this.otherOrientation = otherOrientation;}
        public Integer getRarity(){return rarity;}
        public void setRarity(Integer rarity){this.rarity = rarity;}
        public String getSlug(){return slug;}
        public void setSlug(String slug){this.slug = slug;}
        public CardSetResponseDTO getCardSetResponseDTO(){return cardSet;}
        public void setCardSetResponseDTO(CardSetResponseDTO cardSet){this.cardSet = cardSet;}
        public Integer getThemaCharmFoil(){return themaCharmFoil;}
        public void setThemaCharmFoil(Integer themaCharmFoil){this.themaCharmFoil = themaCharmFoil;}
        public Integer getThemaFerocityFoil(){return themaFerocityFoil;}
        public void setThemaFerocityFoil(Integer themaFerocityFoil){this.themaFerocityFoil = themaFerocityFoil;}
        public Integer getThemaFoil(){return this.themaFoil;}
        public void setThemaFoil(Integer themaFoil){this.themaFoil = themaFoil;}
        public Integer getThemaGraceFoil(){return themaGraceFoil;}
        public void setThemaGraceFoil(Integer themaGraceFoil){this.themaGraceFoil = themaGraceFoil;}
        public Integer getThemaMystiqueFoil(){return themaMystiqueFoil;}
        public void setThemaMystiqueFoil(Integer themaMystiqueFoil){this.themaMystiqueFoil = themaMystiqueFoil;}
        public Integer getThemaValorFoil(){return themaValorFoil;}
        public void setThemaValorFoil(Integer themaValorFoil){this.themaValorFoil = themaValorFoil;}
        public Integer getThemaCharmNonfoil(){return themaCharmNonfoil;}
        public void setThemaCharmNonfoil(Integer themaCharmNonfoil){this.themaCharmNonfoil = themaCharmNonfoil;}
        public Integer getThemaFerocityNonfoil(){return themaFerocityNonfoil;}
        public void setThemaFerocityNonfoil(Integer themaFerocityNonfoil){this.themaFerocityNonfoil = themaFerocityNonfoil;}
        public Integer getThemaNonfoil(){return this.themaNonfoil;}
        public void setThemaNonfoil(Integer themaNonfoil){this.themaNonfoil = themaNonfoil;}
        public Integer getThemaGraceNonfoil(){return themaGraceNonfoil;}
        public void setThemaGraceNonfoil(Integer themaGraceNonfoil){this.themaGraceNonfoil = themaGraceNonfoil;}
        public Integer getThemaMystiqueNonfoil(){return themaMystiqueNonfoil;}
        public void setThemaMystiqueNonfoil(Integer themaMystiqueNonfoil){this.themaMystiqueNonfoil = themaMystiqueNonfoil;}
        public Integer getThemaValorNonfoil(){return themaValorNonfoil;}
        public void setThemaValorNonfoil(Integer themaValorNonfoil){this.themaValorNonfoil = themaValorNonfoil;}
        public String getUUID(){return uuid;}
        public void setUUID(String uuid){this.uuid = uuid;}
    }

    public Set<String> getClasses(){return classes;}
    public void setClasses(Set<String> classes){this.classes = classes;}
    public Integer getCostMemory(){return costMemory;}
    public void setCostMemory(Integer costMemory){this.costMemory = costMemory;}
    public Integer getCostReserve(){return costReserve;}
    public void setCostReserve(Integer costReserve){this.costReserve = costReserve;}
    public Integer getDurability(){return durability;}
    public void setDurability(Integer durability){this.durability = durability;}
    public List<EditionResponseDTO> getEditionResponseDTOs(){return editionResponseDTOs;}
    public void setEditionResponseDTOs(List<EditionResponseDTO> editionResponseDTOs){this.editionResponseDTOs = editionResponseDTOs;}
    public Set<String> getElements(){return elements;}
    public void setElements(Set<String> elements){this.elements = elements;}
    public String getEffect(){return effect;}
    public void setEffect(String effect){this.effect = effect;}
    public String getEffectRaw(){return effectRaw;}
    public void setEffectRaw(String effectRaw){this.effectRaw = effectRaw;}
    public String getFlavor(){return flavor;}
    public void setFlavor(String flavor){this.flavor = flavor;}
    public LocalDateTime getLastUpdate(){return lastUpdate;}
    public void setLastUpdate(LocalDateTime lastUpdate){this.lastUpdate = lastUpdate;}
    public HashMap<String, HashMap<String, Integer>> getLegality(){return legality;}
    public void setLegality(HashMap<String, HashMap<String, Integer>> legality){this.legality = legality;}
    public Integer getLevel(){return level;}
    public void setLevel(Integer level){this.level = level;}
    public Integer getLife(){return life;}
    public void setLife(Integer life){this.life = life;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public Integer getPower(){return power;}
    public void setPower(Integer power){this.power = power;}
    public List<HashMap<String, String>> getReferencedBy(){return referencedBy;}
    public void setReferencedBy(List<HashMap<String, String>> referencedBy){this.referencedBy = referencedBy;}
    public List<HashMap<String, String>> getReferences(){return references;}
    public void setReferences(List<HashMap<String, String>> references){this.references = references;}
    public Set<HashMap<String, String>> getRule(){return rule;}
    public void setRule(Set<HashMap<String, String>> rule){this.rule = rule;}
    public Boolean getSpeed(){return speed;}
    public void setSpeed(Boolean speed){this.speed = speed;}
    public String getSlug(){return slug;}
    public void setSlug(String slug){this.slug = slug;}
    public Set<String> getSubtypes(){return subtypes;}
    public void setSubtypes(Set<String> subtypes){this.subtypes = subtypes;}
    public Set<String> getTypes(){return types;}
    public void setTypes(Set<String> types){this.types = types;}
    public String getUUID(){return uuid;}
    public void setUUID(String uuid){this.uuid = uuid;}
}
