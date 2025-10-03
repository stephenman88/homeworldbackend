package com.arcaelo.homeworldbackend.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import com.arcaelo.homeworldbackend.service.CardSetService;

@Component
public class ApplicationStart implements ApplicationListener<ApplicationReadyEvent>{

    private final CardSetService cardSetService;

    public ApplicationStart(CardSetService cardSetService){
        this.cardSetService = cardSetService;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        cardSetService.pullCardSets();
    }
}
