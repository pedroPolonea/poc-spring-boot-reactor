package com.poc.sbwf.controller;

import com.poc.sbwf.model.documents.Product;
import com.poc.sbwf.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Flux<Product> index() {
        log.info("CM=ProductRestController.index, IN=Begin");

        final Flux<Product> productFlux = productRepository.findAll();

        return productFlux;
    }

    @GetMapping("/{id}")
    public Mono<Product> findId(@PathVariable final String id){
        log.info("CM=ProductRestController.findId, IN=Begin, id={}", id );
        // final Mono<Product> productMono = productRepository.findById(id);


        final Mono<Product> productMono = productRepository.findAll()
                .filter(product -> product.getId().equals(id))
                .next()
                .doOnNext(product -> log.info(product.getName()));

        return productMono;

    }


}
