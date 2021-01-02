package com.poc.sbwf.webflux.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PokemonClient {

    @Value("${client.base.pokemon}")
    private String urlBase;
    @Bean
    public WebClient load(){
        return WebClient.create(urlBase);
    }
}
