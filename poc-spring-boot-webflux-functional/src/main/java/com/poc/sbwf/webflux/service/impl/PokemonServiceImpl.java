package com.poc.sbwf.webflux.service.impl;

import com.poc.sbwf.webflux.config.client.PokemonClient;
import com.poc.sbwf.webflux.router.response.PokemonAllResponse;
import com.poc.sbwf.webflux.service.PokemonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonClient pokemonClient;

    public PokemonServiceImpl(PokemonClient pokemonClient) {
        this.pokemonClient = pokemonClient;
    }

    @Override
    public Flux<PokemonAllResponse> getAll() {
        return pokemonClient.load()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(PokemonAllResponse.class));
    }
}
