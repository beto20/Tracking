package com.example.demo.service;

import com.example.demo.model.dto.AuditDto;
import com.example.demo.model.entity.AuditDocument;
import com.example.demo.repository.AuditRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Mono<Void> save(Mono<AuditDto> auditDto) {
        return auditDto.map(a -> {
            var document = AuditDocument.builder()
                    .setTimestamp(LocalDate.now().toString())
                    .setTraceId(UUID.randomUUID().toString())
                    .build();

            return auditRepository.save(document);
        }).then();
    }
}
