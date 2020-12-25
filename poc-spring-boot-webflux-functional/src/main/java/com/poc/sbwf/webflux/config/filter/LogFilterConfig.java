package com.poc.sbwf.webflux.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class LogFilterConfig implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        final UUID token = UUID.randomUUID();
        final ServerWebExchangeDecorator exchangeDecorator = new LogExchangeDecorator(exchange, token);

        return chain.filter(exchangeDecorator)
                .doOnRequest(aVoid -> {
                    log.info("I=Filter Request, token={}, httpM={}, path={}",token, exchangeDecorator.getRequest().getMethodValue(), exchangeDecorator.getRequest().getPath());
                })
                .doOnSuccess(aVoid -> {
                    log.info("I=Filter Success, token={}, status={}}", token, exchangeDecorator.getResponse().getStatusCode().value());
                })
                .doOnError(throwable -> {
                    log.error("I=Filter Error, token={}, exception={}", token, getException(throwable));
                });
    }

    private Map<String, String> getException(final Throwable throwable){
        if (throwable instanceof ResponseStatusException) {
            final ResponseStatusException exception = (ResponseStatusException) throwable;
            return Map.of("code", String.valueOf(exception.getStatus().value()), "cause", exception.getReason());
        }
        return Map.of("code", "500", "cause", "Default");
    }
}
