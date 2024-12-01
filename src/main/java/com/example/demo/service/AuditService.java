package com.example.demo.service;

import com.example.demo.model.dto.AuditDto;
import reactor.core.publisher.Mono;

public interface AuditService {
    Mono<Void> save(Mono<AuditDto> auditDto);
}
