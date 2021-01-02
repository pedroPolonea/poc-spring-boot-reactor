package com.poc.sbwf.webflux.router;

import com.poc.sbwf.webflux.documents.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsRouterTest {

    @Autowired
    private WebTestClient client;

    @Value("${config.router.products.base}")
    private String url;


    @Test
    void listTest(){

        client.get()
                .uri(url)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBodyList(Product.class);
    }
}