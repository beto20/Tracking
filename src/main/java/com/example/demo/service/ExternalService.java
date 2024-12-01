package com.example.demo.service;

import reactor.core.publisher.Mono;

public interface ExternalService {
    Mono<String> unstableCall();
}
