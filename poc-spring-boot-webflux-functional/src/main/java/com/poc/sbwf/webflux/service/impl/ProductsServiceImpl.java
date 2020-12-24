package com.poc.sbwf.webflux.service.impl;

import com.poc.sbwf.webflux.documents.Product;
import com.poc.sbwf.webflux.repository.ProductsRepository;
import com.poc.sbwf.webflux.service.ProductsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public Flux<Product> getAll() {
        return productsRepository.findAll();
    }

    @Override
    public Mono<Product> getById(final String id) {
        return productsRepository.findById(id);
    }

    @Override
    public Mono<Product> save(final Product product) {
        return productsRepository.save(product);
    }

    @Override
    public Mono<Void> delete(Product product) {
        return productsRepository.delete(product);
    }


}
