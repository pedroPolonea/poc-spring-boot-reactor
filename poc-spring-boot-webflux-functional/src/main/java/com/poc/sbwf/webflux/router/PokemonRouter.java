package com.poc.sbwf.webflux.router;


import com.poc.sbwf.webflux.router.handler.PokemonHandler;
import com.poc.sbwf.webflux.service.PokemonService;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Slf4j
@Configuration
public class PokemonRouter {

    private final String URL_BASE = "/api/pokemon";

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/pokemon", method = RequestMethod.GET ,beanClass = PokemonService.class, beanMethod = "getAll")})
    public RouterFunction<ServerResponse> routerFunctionPokemon(final PokemonHandler pokemonHandler){
        return RouterFunctions
                .route(GET(URL_BASE).and(accept(APPLICATION_JSON)), pokemonHandler::getAll);
    }
}
