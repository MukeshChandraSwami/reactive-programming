package com.learn.reactive.fluxandmono.flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxTest {

    @Test
    public void testFluxOfString(){

        Flux.just("Spring", "DS", "Reactive Programming", "National Holiday")
                .concatWith(Flux.just("Before Error"))
               // .concatWith(Flux.error(() -> new RuntimeException("There is an error")))
                .concatWith(Flux.just("After Error"))
                .log()
                .subscribe(data -> System.out.println(data),
                        e -> {
                            System.out.println("Handling error : " + e.getMessage());
                }, () -> System.out.println("Successfully Completed"));
    }
}
