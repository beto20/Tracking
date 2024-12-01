package com.example.demo.controller;


import com.example.demo.facade.TrackerFacade;
import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.dto.TrackerResponseDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/v1/trackers")
public class TrackerController {

    private final TrackerFacade trackerFacade;

    public TrackerController(TrackerFacade trackerFacade) {
        this.trackerFacade = trackerFacade;
    }

    @GetMapping("/location")
    public Flux<TrackerResponseDto> getCurrentTracker(@RequestParam("lat") float latitude, @RequestParam("long") float longitude) {
        return trackerFacade.getCurrentTracker(latitude, longitude);
    }

    @PostMapping("/register")
    public Mono<Void> saveLocations(@RequestBody TrackerRequestDto trackerRequestDto) {
        return trackerFacade.saveLocations(Mono.just(trackerRequestDto));
    }

    @GetMapping("/error")
    public Mono<Void> throwException() {
        throw new RuntimeException("Forced error for testing");
    }

}
