package com.learn.reactive.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class SampleHandler {


    public Mono<ServerResponse> flux(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.range(1,5).delayElements(Duration.ofSeconds(1)).log()
                        ,Integer.class);
    }

    public Mono<ServerResponse> helloHandler(ServerRequest serverRequest) {

        HandlerFunction<ServerResponse> handlerFunction = request -> {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just("Hello Handler"), String.class);
        };

        return handlerFunction.handle(serverRequest);
    }
}
