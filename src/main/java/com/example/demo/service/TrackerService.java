package com.example.demo.service;

import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TrackerService {

    Flux<TrackerDto> getCurrentLocation(float latitude, float longitude);
    Mono<Void> saveLocations(Mono<TrackerRequestDto> trackerRequestDto);
    Mono<String> callExternalService();
}
