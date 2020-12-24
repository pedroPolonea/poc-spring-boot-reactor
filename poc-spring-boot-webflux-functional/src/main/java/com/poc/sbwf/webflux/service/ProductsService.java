package com.poc.sbwf.webflux.service;

import com.poc.sbwf.webflux.documents.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsService {

    Flux<Product> getAll();

    Mono<Product> getById(String id);

    Mono<Product> save(Product product);

    Mono<Void> delete(Product product);
}
