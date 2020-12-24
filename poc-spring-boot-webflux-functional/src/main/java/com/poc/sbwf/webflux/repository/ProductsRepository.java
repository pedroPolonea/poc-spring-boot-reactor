package com.poc.sbwf.webflux.repository;

import com.poc.sbwf.webflux.documents.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends ReactiveMongoRepository<Product, String> {
}
