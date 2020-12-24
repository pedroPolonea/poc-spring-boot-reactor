package com.poc.sbwf.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class WebfluxFunctionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxFunctionalApplication.class, args);
	}

}
