package com.arcaelo.homeworldbackend.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CardDTO {
    private Set<String> classes;
    private Integer costMemory;
    private Integer costReserve;
    private Integer durability;
    private List<String> editionIds;
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
    private HashMap<String, String> referencedBy;
    private HashMap<String, String> references;
    private Set<HashMap<String, String>> rule;
    private Boolean speed;
    private String slug;
    private Set<String> subtypes;
    private Set<String> types;
    private String uuid;

    public Set<String> getClasses(){return classes;}
    public void setClasses(Set<String> classes){this.classes = classes;}
    public Integer getCostMemory(){return costMemory;}
    public void setCostMemory(Integer costMemory){this.costMemory = costMemory;}
    public Integer getCostReserve(){return costReserve;}
    public void setCostReserve(Integer costReserve){this.costReserve = costReserve;}
    public Integer getDurability(){return durability;}
    public void setDurability(Integer durability){this.durability = durability;}
    public List<String> getEditionIds(){return editionIds;}
    public void setEditionIds(List<String> editionIds){this.editionIds = editionIds;}
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
    public HashMap<String, String> getReferencedBy(){return referencedBy;}
    public void setReferencedBy(HashMap<String, String> referencedBy){this.referencedBy = referencedBy;}
    public HashMap<String, String> getReferences(){return references;}
    public void setReferences(HashMap<String, String> references){this.references = references;}
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
