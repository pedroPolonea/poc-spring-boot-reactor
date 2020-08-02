package com.poc.sbwf.controller;

import com.poc.sbwf.model.documents.Product;
import com.poc.sbwf.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Product>>> index() {
        log.info("CM=ProductRestController.index, IN=Begin");
        return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(productService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>>  findId(@PathVariable final String id){
        log.info("CM=ProductRestController.findId, IN=Begin, id={}", id );

        return productService.findById(id)
                .map(product -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String,Object>>> create(@Valid @RequestBody Mono<Product> product){
        Map<String, Object> response = new HashMap<String, Object>();
        log.info("CM=ProductRestController.create, IN=Begin, product={}", product );
        return product.flatMap(prod -> {
            if(prod.getCreateAt() == null){
                prod.setCreateAt(LocalDateTime.now());
            }
            return productService.save(prod).map(p -> {
                        response.put("product", p);
                        return ResponseEntity
                                .created(URI.create("/aṕi/products/".concat(p.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(response);
                    }
            );
        }).onErrorResume(throwable -> {
           return Mono.just(throwable)
                   .cast(WebExchangeBindException.class)
                   .flatMap(e -> Mono.just(e.getFieldErrors()))
                   .flatMapMany(Flux::fromIterable)
                   .map(error -> "Error fild: "+error.getField()+" "+error.getDefaultMessage())
                   .collectList()
                   .flatMap(errors -> {
                       response.put("errors", errors);
                       response.put("status", HttpStatus.BAD_REQUEST);
                       response.put("timestump", LocalDateTime.now());
                       return Mono.just(ResponseEntity.badRequest().body(response));
                   });
        });

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> update(@PathVariable String id, @RequestBody Product product){
        log.info("CM=ProductRestController.update, IN=Begin, product={}, id={}", product, id );
        return productService.findById(id)
                .flatMap(prod -> renew(prod, product))
                .map(prod -> ResponseEntity.created(URI.create("/aṕi/products/".concat(prod.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(prod)
                ).defaultIfEmpty(ResponseEntity.notFound().build());

    }

    private Mono<Product> renew(Product prodBase, final Product prodUpdate){
        log.info("CM=ProductRestController.renew, IN=Begin, id={}", prodBase.getId() );
        prodBase.setName(prodUpdate.getName());
        prodBase.setPrice(prodUpdate.getPrice());

        return productService.save(prodBase);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        log.info("CM=ProductRestController.delete, IN=Begin, id={}", id);

        return productService.findById(id)
                .flatMap(product -> productService.delete(product)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

    }


}
