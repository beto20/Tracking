package com.example.demo.controller;

import com.example.demo.facade.TrackerFacade;
import com.example.demo.model.dto.TrackerDto;
import com.example.demo.model.dto.TrackerRequestDto;
import com.example.demo.model.dto.TrackerResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class TrackerControllerTest {
    private static TrackerFacade trackerFacade;
    private static TrackerController trackerController;
    private static WebTestClient webTestClient;

    private static final String PATH = "/v1/trackers";

    @BeforeAll
    public static void setup() {
        trackerFacade = Mockito.mock(TrackerFacade.class);
        trackerController = new TrackerController(trackerFacade);
        webTestClient = WebTestClient.bindToController(trackerController).build();
    }

    @Test
    @DisplayName("Case: Successfully - 200")
    void getCurrentTracker_test() {
        var response = TrackerResponseDto.builder()
                .setCountry("Peru")
                .setCity("Lima")
                .setAddress("Surco")
                .build();

        var request = new TrackerRequestDto();
        request.setLatitude(2.0f);
        request.setLongitude(1.0f);

        Mockito.when(trackerFacade.getCurrentTracker(Mockito.anyFloat(), Mockito.anyFloat()))
                .thenReturn(Flux.just(response));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(PATH+"/location")
                        .queryParam("lat", 2.0f)
                        .queryParam("long", 1.0f)
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(TrackerResponseDto.class)
                .hasSize(1)
                .value(c -> {
                    Assertions.assertEquals("Peru", c.get(0).getCountry());
                    Assertions.assertEquals("Lima", c.get(0).getCity());
                    Assertions.assertEquals("Surco", c.get(0).getAddress());
                });
    }

    @Test
    @DisplayName("Case: Failure - 404")
    void getCurrentTracker_test_fail() {
        webTestClient.post()
                .uri(PATH+"/location404")
                .bodyValue(TrackerResponseDto.builder().build())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("Case: Failure - 500")
    void getCurrentTracker_test_fail_server() {
        webTestClient.get()
                .uri(PATH+"/error")
                .exchange()
                .expectStatus()
                .is5xxServerError();
//                .expectBody(String.class)
//                .value(body -> {
//                    assert body.contains("Forced error for testing");
//                });
    }

    @Test
    @DisplayName("Case: Successfully - 200")
    void saveLocations_test() {
        Mockito.when(trackerFacade.saveLocations(Mockito.any()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri(PATH+"/register")
                .bodyValue(new TrackerRequestDto())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
