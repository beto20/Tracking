package com.example.demo.service;

import com.example.demo.model.dto.AuditDto;
import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.entity.TrackerEntity;
import com.example.demo.model.mapper.TrackerMapper;
import com.example.demo.repository.TrackerCache;
import com.example.demo.repository.TrackerRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.SQLException;


@Service
public class TrackerServiceImpl implements TrackerService {
    private final TrackerRepository trackerRepository;
    private final TrackerCache trackerCache;
    private final AuditService auditService;
    private final ExternalService externalService;

    @Value("${demo.has-cache:false}")
    private boolean hasCache;

    public TrackerServiceImpl(TrackerRepository trackerRepository, TrackerCache trackerCache, AuditService auditService, ExternalService externalService) {
        this.trackerRepository = trackerRepository;
        this.trackerCache = trackerCache;
        this.auditService = auditService;
        this.externalService = externalService;
    }

    @Override
    public Flux<TrackerDto> getCurrentLocation(float latitude, float longitude) {
        return trackerRepository.findByLatitudeAndLongitude(latitude, longitude)
                .switchIfEmpty(Mono.error(new SQLException("No data found")))
                .onErrorResume(SQLException.class, e -> Mono.error(new SQLException("DB error", e.getMessage())))
                .map(te -> TrackerDto.builder()
                            .setAddress(te.getAddress())
                            .setCity(te.getCity())
                            .setCountry(te.getCountry())
                            .build())
                .doOnNext(x -> auditService.save(Mono.just(AuditDto.builder().build())));
    }

    public Flux<TrackerDto> get(float latitude, float longitude) {
        return trackerRepository.findByLatitudeAndLongitude(latitude, longitude)
                .map(TrackerMapper.mapper::toDto)
                .doOnNext(x -> trackerCache.saveLocation(latitude, longitude, x));
    }


    @Override
    public Mono<Void> saveLocations(Mono<TrackerRequestDto> trackerRequestDto) {
        return trackerRequestDto
                .flatMap(dto -> {
                    TrackerEntity entity = TrackerEntity.builder()
                            .setLatitude(dto.getLatitude())
                            .setLongitude(dto.getLongitude())
                            .setCountry(dto.getCountry())
                            .setCity(dto.getCity())
                            .setAddress(dto.getAddress())
                            .build();

                    return trackerRepository.save(entity).then();
                })
                .doOnNext(x -> auditService.save(Mono.just(AuditDto.builder().build())))
                .then();
    }

    @CircuitBreaker(name = "trackerService", fallbackMethod = "fallback")
    @Retry(name = "trackerService")
    public Mono<String> callExternalService() {
        return externalService.unstableCall();
    }

    public Mono<String> fallback(Throwable throwable) {
        return Mono.just("Fallback response: " + throwable.getMessage());
    }
}
