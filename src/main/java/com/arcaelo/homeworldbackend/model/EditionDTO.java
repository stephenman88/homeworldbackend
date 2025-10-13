package com.arcaelo.homeworldbackend.model;

import java.util.Set;
import java.util.HashSet;

public class EditionDTO {
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
    private Set<String> otherOrientationCardId;
    private Integer rarity;
    private String slug;
    private String cardSetId;
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
    private Set<String> otherOrientationEditionIds;

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
    public Set<String> getOtherOrientationCardId(){return otherOrientationCardId;}
    public void setOtherOrientationCardId(Set<String> otherOrientationCardId){this.otherOrientationCardId = otherOrientationCardId;}
    public void setOtherOrientationCardId(String otherOrientationCardId)
    {
        Set<String> list = new HashSet<String>();
        list.add(otherOrientationCardId);
        this.otherOrientationCardId = list;}
    public Integer getRarity(){return rarity;}
    public void setRarity(Integer rarity){this.rarity = rarity;}
    public String getSlug(){return slug;}
    public void setSlug(String slug){this.slug = slug;}
    public String getCardSetId(){return cardSetId;}
    public void setCardSetId(String cardSetId){this.cardSetId = cardSetId;}
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
    
    public Set<String> getOtherOrientationEditionIds(){return otherOrientationEditionIds;}
    public void setOtherOrientationEditionIds(Set<String> otherOrientationEditionIds){this.otherOrientationEditionIds = otherOrientationEditionIds;}
}
