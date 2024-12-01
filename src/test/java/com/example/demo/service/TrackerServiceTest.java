package com.example.demo.service;

import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.entity.TrackerEntity;
import com.example.demo.repository.TrackerCache;
import com.example.demo.repository.TrackerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TrackerServiceTest {

    private static TrackerRepository trackerRepository;
    private static TrackerCache trackerCache;
    private static TrackerService trackerService;

    @BeforeAll
    public static void setup() {
        trackerRepository = Mockito.mock(TrackerRepository.class);
        trackerCache = Mockito.mock(TrackerCache.class);
        trackerService = new TrackerServiceImpl(trackerRepository, trackerCache);
    }


    @Test
    @DisplayName("Case: Success")
    void saveLocations_test() {
        var dto = new TrackerRequestDto();
        dto.setLatitude(100.00f);
        dto.setLongitude(200.00f);
        dto.setCountry("Peru");
        dto.setCity("Lima");
        dto.setAddress("Surco");

//        Mockito.when(trackerRepository.save(Mockito.any()))
//                .thenReturn(Mono.just(new TrackerEntity()));

        var result = trackerService.saveLocations(Mono.just(dto));

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        Mockito.verify(trackerRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("Case: Success")
    void getCurrentLocation_test() {
        var dto = TrackerDto.builder()
                .setCountry("Peru")
                .setCity("Lima")
                .setAddress("Surco")
                .build();

//        Mockito.when(trackerRepository.findByLatitudeAndLongitude(Mockito.any(), Mockito.any()))
//                .thenReturn(Flux.just(new TrackerEntity()));

        var result = trackerService.getCurrentLocation(100.00f, 200.00f);

        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();


    }
}
