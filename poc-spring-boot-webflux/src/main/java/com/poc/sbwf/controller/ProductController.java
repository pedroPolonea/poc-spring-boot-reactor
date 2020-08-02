package com.poc.sbwf.controller;

import com.poc.sbwf.model.documents.Product;
import com.poc.sbwf.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping({"/list", "/"})
    public String findAll(Model model){
        log.info("CM=ProductController.findAll, IN=Begin");

        Flux<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("title", "Lista de produtos");

        return "list";
    }

    @GetMapping("/list-datadriver")
    public String findAllDataDrive(Model model){
        log.info("CM=ProductController.findAllDataDrive, IN=Begin");

        Flux<Product> products = productService.findAll().delayElements(Duration.ofSeconds(1));

        model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 1 ) );
        model.addAttribute("title", "Lista de produtos");

        return "list";
    }

    @GetMapping("/list-full")
    public String findFull(Model model){
        log.info("CM=ProductController.findFull, IN=Begin");

        Flux<Product> products = productService.findAll()
                .repeat(5000);
        model.addAttribute("products", products);
        model.addAttribute("title", "Lista de produtos");

        return "list";
    }

    @GetMapping("/list-chunk")
    public String findAllChunck(Model model){
        log.info("CM=ProductController.findAllChunck, IN=Begin");

        Flux<Product> products = productService.findAll()
                .repeat(5000);
        model.addAttribute("products", products);
        model.addAttribute("title", "Lista de produtos chunk");

        return "list-chunk";
    }

    @GetMapping("/form")
    public Mono<String> registerForm(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("title", "Register of Product");
        return Mono.just("form");
    }

    @PostMapping("/form")
    public Mono<String> save(Product product){
        log.info("CM=ProductController.save, IN=Begin, product={}", product);
        productService.save(product);

        return Mono.just("redirect:/list");
    }
}
