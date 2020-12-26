package com.poc.sbwf.webflux.handler;

import com.poc.sbwf.webflux.documents.Product;
import com.poc.sbwf.webflux.service.ProductsService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component
public class ProductsHandler {

    private ProductsService productsService;

    private Validator validator;

    public ProductsHandler(ProductsService productsService, Validator validator) {
        this.productsService = productsService;
        this.validator = validator;
    }

    public Mono<ServerResponse> getAll(final ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productsService.getAll(), Product.class);
    }

    public Mono<ServerResponse> getById(final ServerRequest request){
        final String id = request.pathVariable("id");

        return productsService.getById(id).flatMap(product ->
                ServerResponse.ok().body(BodyInserters.fromValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> save(final ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);

        return product.flatMap(p -> {
                    validate(p);

                    return productsService.save(p)
                            .flatMap(productSave ->
                                    ServerResponse.created(createURI(request, productSave.getId()))
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(BodyInserters.fromValue(productSave))
                            );
                }
        );
    }

    public Mono<ServerResponse> update(final ServerRequest request){
        Mono<Product> productRequest = request.bodyToMono(Product.class);
        final String id = request.pathVariable("id");

        return productsService.getById(id).zipWith(productRequest, mix())
                .flatMap(productsService::save)
                .flatMap(p ->ServerResponse.ok().body(BodyInserters.fromValue(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(final ServerRequest request){
        final String id = request.pathVariable("id");

        return productsService.getById(id)
                .flatMap(product ->
                        productsService.delete(product)
                                .then(ServerResponse.noContent().build())
                ).switchIfEmpty(ServerResponse.notFound().build());
    }

    private BiFunction<Product, Product, Product> mix() {
        return (base, resquest) -> {
            base.setId(resquest.getId());
            base.setName(resquest.getName());
            base.setCreateAt(resquest.getCreateAt());
            base.setPrice(resquest.getPrice());
            return base;
        };
    }

    private URI createURI(final ServerRequest request, final String id){
        return URI.create(request.uri().toString().concat("/").concat(id));
    }

    private void validate(final Product product){
        Errors errors = new BeanPropertyBindingResult(product, Product.class.getName());
        validator.validate(product, errors);

        if (errors.hasErrors()) {
            String errorsTreated = errors.getFieldErrors()
                    .stream()
                    .map(fieldError -> "O campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList())
                    .toString();

            throw new ServerWebInputException(errorsTreated);
        }

    }

}
