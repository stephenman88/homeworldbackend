package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.List;

@Entity
@Table(name = "editions")
public class Edition {
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "uuid")
    private Card card;
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
    @OneToMany
    private List<Card> otherOrientation; 
    private Integer rarity;//start here
    private String slug;
    @ManyToOne
    @JoinColumn(name = "cardset_id", referencedColumnName = "id")
    private CardSet cardSet;
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
    @Id
    private String uuid;

    public Card getCard(){return card;}
    public void setCard(Card card){this.card = card;}
    public Set<String> getCollaborators(){return collaborators;}
    public void setCollaborators(Set<String> collaborators){this.collaborators = collaborators;}
    public String getCollectorNumber(){return collectorNumber;}
    public void setCollectorNumber(String collectorNumber){this.collectorNumber = collectorNumber;}
    public String getConfiguration(){return configuration;}
    public void setConfiguration(String configuration){this.configuration = configuration;}
    public String getEffect(){return effect;}
    public void setEffect(String effect){this.effect = effect;}
    public String getEffectHtml(){return effectHtml;}
    public void setEffectHtml(String effectHtml){this.effect = effectHtml;}
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
    public List<Card> getOtherOrientation(){return otherOrientation;}
    public void setOtherOrientation(List<Card> otherOrientation){this.otherOrientation = otherOrientation;}
    public Integer getRarity(){return rarity;}
    public void setRarity(Integer rarity){this.rarity = rarity;}
    public String getSlug(){return slug;}
    public void setSlug(String slug){this.slug = slug;}
    public CardSet getCardSet(){return cardSet;}
    public void setCardSet(CardSet cardSet){this.cardSet = cardSet;}
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
