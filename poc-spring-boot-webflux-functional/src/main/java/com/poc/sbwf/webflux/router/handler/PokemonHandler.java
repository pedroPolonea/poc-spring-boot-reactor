package com.poc.sbwf.webflux.router.handler;

import com.poc.sbwf.webflux.router.response.PokemonAllResponse;
import com.poc.sbwf.webflux.service.PokemonService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PokemonHandler {

    private PokemonService pokemonService;

    public PokemonHandler(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    public Mono<ServerResponse> getAll(final ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pokemonService.getAll(), PokemonAllResponse.class);
    }
}
