package com.poc.sbwf.service.impl;

import com.poc.sbwf.controller.ProductController;
import com.poc.sbwf.model.documents.Product;
import com.poc.sbwf.model.repository.ProductRepository;
import com.poc.sbwf.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
        log.info("CM=ProductServiceImpl.findById, IN=Begin, id={}", id );
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> save(Product product) {
        log.info("CM=ProductServiceImpl.save, IN=Begin, product={}", product );
        return productRepository.save(product);
    }

    @Override
    public Mono<Void> delete(Product product) {
        log.info("CM=ProductServiceImpl.delete, IN=Begin, product={}", product );
        return productRepository.delete(product);
    }
}
