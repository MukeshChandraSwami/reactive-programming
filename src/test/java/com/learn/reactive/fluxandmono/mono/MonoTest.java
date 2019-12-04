package com.learn.reactive.fluxandmono.mono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    public void monoTest(){

        Mono<String> mono = Mono.just("Reactive Programming")
                .log();

        mono.subscribe(System.out::println);
    }

}
