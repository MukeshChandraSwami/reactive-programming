package com.learn.reactive.fluxandmono.flux;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DirtiesContext
public class BackPressureSupportTest {

    @Test
    public void testBackPressure_onRequest(){

        Flux<Integer> flux = Flux.range(1,10)
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(2)
                .expectNext(2)
                .thenCancel()
                .verify();

    }

    @Test
    public void testBackPressure_onRequest_withSubscriber() {

        Flux<Integer> flux = Flux.range(1, 10)
                .log();

        flux.subscribe(i -> System.out.println(i),
                e -> System.err.println(e),
                () -> System.out.println("Done"),
                subscription -> {
                    subscription.request(2);
                    subscription.cancel();
                    subscription.request(2);
                    subscription.request(2);
                    subscription.request(2);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void testBackPressure_customBackpressure(){

        Flux<Integer> flux = Flux.range(1, 10)
                .log();

        flux.subscribe(new BaseSubscriber<Integer>() {
            @SneakyThrows
            @Override
            protected void hookOnNext(Integer value) {

                System.out.println("Value is : " + value);
                Thread.sleep(1000);
                request(2);
            }

        });
    }
}
