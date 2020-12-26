package com.poc.sbwf.webflux.router;

import com.poc.sbwf.webflux.handler.ProductsHandler;
import com.poc.sbwf.webflux.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Configuration
public class ProductsRouter {
    private final String PRODUCT_URL_BASE = "/api/products";
    private final String PRODUCT_URL_BASE_ID = PRODUCT_URL_BASE.concat("/{id}");

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/products", method = RequestMethod.GET ,beanClass = ProductsService.class, beanMethod = "getAll"),
            @RouterOperation(path = "/api/products/{id}", method = RequestMethod.GET , beanClass = ProductsService.class, beanMethod = "getById", operation = @Operation(operationId = "findById", parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Product Id") })),
            @RouterOperation(path = "/api/products/{id}", method = RequestMethod.PUT , beanClass = ProductsService.class, beanMethod = "save", operation = @Operation(operationId = "update", parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Product Id") })),
            @RouterOperation(path = "/api/products", method = RequestMethod.POST , beanClass = ProductsService.class, beanMethod = "save"),
            @RouterOperation(path = "/api/products/{id}", method = RequestMethod.DELETE , beanClass = ProductsService.class, beanMethod = "delete")})
    public RouterFunction<ServerResponse> routerFunctionProduct(final ProductsHandler productsHandler){
        return RouterFunctions
                .route(GET(PRODUCT_URL_BASE).and(accept(APPLICATION_JSON)), productsHandler::getAll)
                .andRoute(GET(PRODUCT_URL_BASE_ID).and(accept(APPLICATION_JSON)), productsHandler::getById)
                .andRoute(POST(PRODUCT_URL_BASE).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), productsHandler::save)
                .andRoute(PUT(PRODUCT_URL_BASE_ID).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), productsHandler::update)
                .andRoute(DELETE(PRODUCT_URL_BASE_ID), productsHandler::delete);
    }
}
