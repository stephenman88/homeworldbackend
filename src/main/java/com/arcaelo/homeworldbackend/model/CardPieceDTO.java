package com.arcaelo.homeworldbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardPieceDTO {
    private String cardDataUUID;
    private String editionDataUUID;
    private String imageUrl;

    public String getCardDataUUID(){return cardDataUUID;}
    @JsonProperty("card_data_uuid")
    public void setCardDataUUID(String cardDataUUID){this.cardDataUUID = cardDataUUID;}

    public String getEditionDataUUID(){return editionDataUUID;}
    @JsonProperty("edition_data_uuid")
    public void setEditionDataUUID(String editionDataUUID){
        this.editionDataUUID = editionDataUUID;
    }

    public String getImageUrl(){return imageUrl;}
    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}
}
