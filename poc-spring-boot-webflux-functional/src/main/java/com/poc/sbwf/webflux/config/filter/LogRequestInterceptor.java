package com.poc.sbwf.webflux.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.UUID;

@Slf4j
public class LogRequestInterceptor extends ServerHttpRequestDecorator {

    private UUID token;

    public LogRequestInterceptor(ServerHttpRequest delegate, UUID token) {
        super(delegate);
        this.token = token;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        return super.getBody().doOnNext(dataBuffer -> {
            try {
                Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                String body = IOUtils.toString(baos.toByteArray(), "UTF-8");

                log.info("M=LogRequestInterceptor.getBody, httpM={}, token={}, path={}, payload={}",
                        getDelegate().getMethod(), token, getDelegate().getPath(), body);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public MultiValueMap<String, String> getQueryParams() {
        log.info("M=LogRequestInterceptor.getQueryParams, httpM={}, token={}, path={}, queryParams={}",
                getDelegate().getMethod(), token, getDelegate().getPath(), getDelegate().getQueryParams());
        return getDelegate().getQueryParams();
    }

}