package com.example.demo.repository;

import com.example.demo.model.entity.TrackerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TrackerRepository extends R2dbcRepository<TrackerEntity, Integer> {

    Flux<TrackerEntity> findByLatitudeAndLongitude(float lat, float lon);
}
