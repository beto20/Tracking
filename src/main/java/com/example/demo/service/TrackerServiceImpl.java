package com.example.demo.service;

import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.entity.TrackerEntity;
import com.example.demo.model.mapper.TrackerMapper;
import com.example.demo.repository.TrackerCache;
import com.example.demo.repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.SQLException;


@Service
public class TrackerServiceImpl implements TrackerService {
    private final TrackerRepository trackerRepository;
    private final TrackerCache trackerCache;

    @Value("${demo.has-cache:false}")
    private boolean hasCache;

    public TrackerServiceImpl(TrackerRepository trackerRepository, TrackerCache trackerCache) {
        this.trackerRepository = trackerRepository;
        this.trackerCache = trackerCache;
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
                            .build());
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
                .then();
    }
}
