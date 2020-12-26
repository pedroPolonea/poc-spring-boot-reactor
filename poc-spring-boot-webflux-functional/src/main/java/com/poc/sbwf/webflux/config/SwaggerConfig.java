package com.poc.sbwf.webflux.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.webflux.core.converters.WebFluxSupportConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        ModelConverters.getInstance().addConverter(new WebFluxSupportConverter());
        return new OpenAPI()
                .info(new Info()
                        .title("API")
                        .description("WS restful API")
                        .version("v0.0.1")
                        .license(new License().name("License of API"))
                );
    }




}
