package com.poc.sbwf.webflux.config.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

import java.util.UUID;

public class LogExchangeDecorator extends ServerWebExchangeDecorator {

    private UUID token;

    protected LogExchangeDecorator(ServerWebExchange delegate, UUID token) {
        super(delegate);
        this.token = token;
    }

    @Override
    public ServerHttpRequest getRequest() {
        return new LogRequestInterceptor(super.getRequest(), token);
    }

    @Override
    public ServerHttpResponse getResponse() {
        return new LogResponseInterceptor(super.getResponse(), token);
    }
}
