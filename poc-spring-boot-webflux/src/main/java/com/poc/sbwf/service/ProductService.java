package com.poc.sbwf.service;

import com.poc.sbwf.model.documents.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> findAll();

    Mono<Product> findById(final String id);

    Mono<Product> save(final Product product);

    Mono<Void> delete(final Product product);

}
