package com.arcaelo.homeworldbackend.model;

public class CardPieceDTO {
    private String cardDataUUID;
    private String editionDataUUID;
    private String imageUrl;

    public String getCardDataUUID(){return cardDataUUID;}
    public void setCardDataUUID(String cardDataUUID){this.cardDataUUID = cardDataUUID;}

    public String getEditionDataUUID(){return editionDataUUID;}
    public void setEditionDataUUID(String editionDataUUID){
        this.editionDataUUID = editionDataUUID;
    }

    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}
}
