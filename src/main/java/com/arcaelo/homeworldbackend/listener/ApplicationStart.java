package com.arcaelo.homeworldbackend.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.arcaelo.homeworldbackend.service.CardService;

import org.springframework.boot.context.event.ApplicationReadyEvent;

@Component
@Profile("!test")
public class ApplicationStart implements ApplicationListener<ApplicationReadyEvent>{

    private final CardService cardService;

    public ApplicationStart(CardService cardService){
        this.cardService = cardService;
    }

    @Override
    public void onApplicationEvent(final @NonNull ApplicationReadyEvent event) {
        cardService.pullAllCards();
    }
}
