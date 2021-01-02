package com.poc.sbwf.webflux.service;

import com.poc.sbwf.webflux.router.response.PokemonAllResponse;
import reactor.core.publisher.Flux;

public interface PokemonService {

    Flux<PokemonAllResponse> getAll();
}
