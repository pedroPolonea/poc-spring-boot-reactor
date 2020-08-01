package com.poc.sbwf;

import com.poc.sbwf.model.documents.Product;
import com.poc.sbwf.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PocSpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	private static final Logger log = LoggerFactory.getLogger(PocSpringBootWebfluxApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(PocSpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.just(new Product("TV", 600.50),
				new Product("Radio", 100.50),
				new Product("Computador", 900.50),
				new Product("Fogão", 434.25),
				new Product("Geladeira", 999.10),
				new Product("Notebook", 910.50)
		)
		.flatMap(productRepository::save)
		.subscribe(productMono -> log.info("Id: {}, nome:{}", productMono.getId(), productMono.getName()));
	}
}
