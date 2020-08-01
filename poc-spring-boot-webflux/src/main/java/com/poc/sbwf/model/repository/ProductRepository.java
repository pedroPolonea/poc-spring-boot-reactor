package com.poc.sbwf.model.repository;

import com.poc.sbwf.model.documents.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
