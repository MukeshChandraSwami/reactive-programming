package com.learn.reactive.fluxandmono.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

public class FluxTest {

    @Test
    public void testFluxOfString(){

        Flux.just("Spring", "DS", "Reactive Programming", "National Holiday")
                .concatWith(Flux.just("Before Error"))
                .concatWith(Flux.error(() -> new RuntimeException("There is an error")))
                .concatWith(Flux.just("After Error"))
                .mergeWith(Flux.just("Merged Flux"))
                .log()
                .subscribe(data -> System.out.println(data),
                        e -> {
                            System.out.println("Handling error : " + e.getMessage());
                }, () -> System.out.println("Successfully Completed"));
    }

    @Test
    public void fluxTest_without_error(){

        Flux<String> flux = Flux.just("Spring", "DS", "Reactive Programming", "National Holiday")
                .log();
        StepVerifier.create(flux, StepVerifierOptions.create())
                .expectNext("Spring")
                .expectNext("DS","Reactive Programming","National Holiday")
                .expectComplete()
                .verify();
    }

    @Test
    public void fluxTest_with_error(){

        Flux<String> flux = Flux.just("Spring", "DS", "Reactive Programming", "National Holiday")
                .concatWith(Flux.error(() -> new NullPointerException("There is some issue")))
                .log();

        StepVerifier.create(flux, StepVerifierOptions.create())
                .expectNext("Spring")
                .expectNext("DS","Reactive Programming","National Holiday")
                .verifyError();
    }

    @Test
    public void fluxTest_with_error_publish_data_counter(){

        Flux<String> flux = Flux.just("Spring", "DS", "Reactive")
                .log()
                .concatWith(Flux.error(new RuntimeException()));

        StepVerifier.create(flux)
                .expectNext("Spring")
                .expectNextCount(2)
                .verifyError(Exception.class);
    }

}
