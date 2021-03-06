package com.learn.reactive.fluxandmono.flux;

import lombok.SneakyThrows;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DirtiesContext
public class HotAndColdPublisher {

    @SneakyThrows
    @Test
    public void testColdPublisher() {

        Publisher<Integer> publisher = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .log();

        // These all are cold subscriber they will start subscribing from beginning

        ((Flux<Integer>) publisher).subscribe(i -> System.out.println("Subscriber 1 :- " + i));
        Thread.sleep(5000);

        ((Flux<Integer>) publisher).subscribe(i -> System.out.println("Subscriber 2 :- " + i));
        Thread.sleep(6000);

        ((Flux<Integer>) publisher).subscribe(i -> System.out.println("Subscriber 3 :- " + i));
        Thread.sleep(9000);
    }

    @Test
    public void testHotPublisher_publish() throws InterruptedException {
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));

        ConnectableFlux<Long> connectableFlux = flux.publish();
        connectableFlux.connect();



        connectableFlux.subscribe(i -> System.out.println("Clock 1 :- " + i));
        Thread.sleep(2000);

        connectableFlux.subscribe(i -> System.out.println("\tClock 2 :- " + i));
        Thread.sleep(2000);
    }


    @Test
    public void testHotPublisher_share() throws InterruptedException {

        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));
        Flux<Long> hotFLux = flux.share();

        hotFLux.subscribe(i -> System.out.println("Clock 1 :- " + i));
        Thread.sleep(2000);

        hotFLux.subscribe(i -> System.out.println("\tClock 2 :- " + i));
        Thread.sleep(2000);
    }
}
