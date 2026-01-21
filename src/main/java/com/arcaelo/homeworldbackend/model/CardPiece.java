package com.arcaelo.homeworldbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="card_piece")
public class CardPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "uuid")
    private Card cardData;
    @ManyToOne
    @JoinColumn(name = "edition_id", referencedColumnName = "uuid")
    private Edition editionData;
    private String imageUrl;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public Card getCardData(){return cardData;}
    public void setCardData(Card cardData){this.cardData = cardData;}

    public Edition getEditionData(){return editionData;}
    public void setEditionData(Edition editionData){
        this.editionData = editionData;
        imageUrl = editionData.getImage();
    }

    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}
}
