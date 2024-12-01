package com.example.demo.facade;

import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.dto.TrackerResponseDto;
import com.example.demo.model.mapper.TrackerMapper;
import com.example.demo.model.mapper.TrackerMapperRx;
import com.example.demo.service.TrackerService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TrackerFacade {

    private final TrackerService trackerService;

    public TrackerFacade(TrackerService trackerService) {
        this.trackerService = trackerService;
    }


    public Flux<TrackerResponseDto> getCurrentTracker(float latitude, float longitude) {
        return trackerService.getCurrentLocation(latitude, longitude)
                .map(TrackerMapperRx::toResponseDto);
    }

    public Mono<Void> saveLocations(Mono<TrackerRequestDto> trackerRequestDto) {
        return trackerService.saveLocations(trackerRequestDto).log();
    }
}
