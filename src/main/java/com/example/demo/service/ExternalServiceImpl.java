package com.example.demo.service;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class ExternalServiceImpl implements ExternalService {

    public Mono<String> unstableCall() {
        return Mono.delay(Duration.ofSeconds(3))
                .map(i -> {
                    if (Math.random() > 0.5) {
                        throw new RuntimeException("Service failure");
                    }
                    return "Success";
                });
    }
}
