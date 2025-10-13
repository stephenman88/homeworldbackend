package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.*;

import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name="cards")
public class Card {
    private Set<String> classes;
    private Integer costMemory;
    private Integer costReserve;
    private Integer durability;
    @OneToMany(mappedBy="card", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Edition> editions;
    private Set<String> elements;
    @Column(columnDefinition = "text")
    private String effect;
    @Column(columnDefinition = "text")
    private String effectRaw;
    @Column(columnDefinition = "text")
    private String flavor;
    private LocalDateTime lastUpdate;
    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonConverters.HashMapHashMapConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private HashMap<String, HashMap<String, Integer>> legality;
    private Integer level;
    private Integer life;
    private String name;
    private Integer power;
    @Column(name="card_referenced_by", columnDefinition = "jsonb")
    @Convert(converter = JsonConverters.HashMapListConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<HashMap<String, String>> referencedBy;
    @Column(name="card_references", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonConverters.HashMapListConverter.class)
    private List<HashMap<String, String>> references;
    @Column(name="card_rule", columnDefinition = "jsonb")
    @Convert(converter = JsonConverters.HashMapSetConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<HashMap<String, String>> rule;
    private Boolean speed;
    private String slug;
    @ElementCollection
    private Set<String> subtypes;
    @ElementCollection
    private Set<String> types;
    @Id
    private String uuid;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Card)) return false;
        Card card = (Card) o;
        return uuid != null && this.uuid.equals(card.getUUID());
    }

    @Override
    public int hashCode(){
        return uuid != null ? uuid.hashCode() : 0;
    }

    public Set<String> getClasses(){return classes;}
    public void setClasses(Set<String> classes){this.classes = classes;}
    public Integer getCostMemory(){return costMemory;}
    public void setCostMemory(Integer costMemory){this.costMemory = costMemory;}
    public Integer getCostReserve(){return costReserve;}
    public void setCostReserve(Integer costReserve){this.costReserve = costReserve;}
    public Integer getDurability(){return durability;}
    public void setDurability(Integer durability){this.durability = durability;}
    public List<Edition> getEditions(){return editions;}
    public void setEditions(List<Edition> editions){this.editions = editions;}
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