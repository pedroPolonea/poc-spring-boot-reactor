package com.poc.sbwf.webflux.handler;

import com.poc.sbwf.webflux.documents.Product;
import com.poc.sbwf.webflux.service.ProductsService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.BiFunction;

@Component
public class ProductsHandler {

    private ProductsService productsService;

    public ProductsHandler(ProductsService productsService) {
        this.productsService = productsService;
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

        return product.flatMap(productsService::save)
                .flatMap(p ->
                    ServerResponse.created(createURI(request, p.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(p))
                ).switchIfEmpty(ServerResponse.badRequest().build());
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

}
