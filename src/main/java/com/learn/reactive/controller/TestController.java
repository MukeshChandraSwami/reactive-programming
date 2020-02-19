package com.learn.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class TestController {

    @GetMapping(path = "/flux",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getStringFlux(){

        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
