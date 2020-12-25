package com.poc.sbwf.webflux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class LogFilterConfig implements WebFilter {
    private UUID generator;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();

        generator = UUID.randomUUID();
        final long startTime = System.currentTimeMillis();

        ServerWebExchangeDecorator exchangeDecorator = new ServerWebExchangeDecorator(exchange) {
            @Override
            public ServerHttpRequest getRequest() {
                return new RequestLoggingInterceptor(super.getRequest());
            }

            @Override
            public ServerHttpResponse getResponse() {
                return new ResponseLoggingInterceptor(super.getResponse(), startTime);
            }
        };

        return chain.filter(exchangeDecorator)
                .doOnRequest(aVoid -> {
                    log.info("{}, {}, {}, {}",startTime, exchangeDecorator.getRequest(), exchangeDecorator.getRequest());
                })
                .doOnSuccess(aVoid -> {
                    log.info("{}, {}, {}, {}",startTime, exchangeDecorator.getResponse(), exchangeDecorator.getResponse().getStatusCode().value());
                })
                .doOnError(throwable -> {
                    log.error("{}, {}, {}, {}", startTime, exchangeDecorator.getResponse(), 500);
                });
    }
}
