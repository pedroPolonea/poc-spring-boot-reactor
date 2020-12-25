package com.poc.sbwf.webflux.router;

import com.poc.sbwf.webflux.handler.ProductsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Configuration
public class ProductsRouter {
    private final String PRODUCT_URL_BASE = "/api/v1/products";
    private final String PRODUCT_URL_BASE_ID = PRODUCT_URL_BASE.concat("/{id}");

    @Bean
    public RouterFunction<ServerResponse> routerFunctionProduct(final ProductsHandler productsHandler){
        return RouterFunctions
                .route(GET(PRODUCT_URL_BASE), productsHandler::getAll)
                .andRoute(GET(PRODUCT_URL_BASE_ID), productsHandler::getById)
                .andRoute(POST(PRODUCT_URL_BASE), productsHandler::save)
                .andRoute(PUT(PRODUCT_URL_BASE_ID), productsHandler::update)
                .andRoute(DELETE(PRODUCT_URL_BASE_ID), productsHandler::delete);
    }
}
