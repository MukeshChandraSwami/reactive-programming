package com.learn.reactive.fluxandmono;


import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 *  Combinations of Flux and Mono with each other.
 */
public class FluxAndMonoCombineTests {

    private static final List<String> techs = Arrays.asList("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor");

    @Test
    public void testFlux_Merge_Fluxes(){

        Flux<String> flux = Flux.merge(Flux.just("A","B","C"), Flux.fromIterable(techs));
        StepVerifier.create(flux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNextCount(techs.size())
                .verifyComplete();
    }

    /**
     *  Sequence will not be guarantee after adding delay of publishing data.
     *  This data can be in any order.
     */
    @Test
    public void testFlux_Merge_Fluxes_With_Delay(){

        Flux<String> flux = Flux.merge(Flux.just("A","B","C").delayElements(Duration.ofSeconds(1))
                , Flux.fromIterable(techs).delayElements(Duration.ofSeconds(1)));
        StepVerifier.create(flux.log())
                .expectSubscription()
                //.expectNext("A","B","C")
                .expectNextCount(techs.size() + 3)
                .verifyComplete();
    }

    @Test
    public void testFlux_Concat(){
        Flux<String> flux = Flux.concat(Flux.just("A","B","C"), Flux.fromIterable(techs));
        StepVerifier.create(flux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNextCount(techs.size())
                .verifyComplete();
    }

    /**
     *  Sequence will  be guarantee even after adding delay of publishing data.
     *  This data will be in order of FCFS.
     */

    @Test
    public void testFlux_Concat_With_Delay(){
        Flux<String> flux = Flux.concat(Flux.just("A","B","C").delayElements(Duration.ofSeconds(1)),
                Flux.fromIterable(techs));
        StepVerifier.create(flux.log())
                .expectSubscription()
                //.expectNext("A","B","C")
                .expectNextCount(techs.size() + 3)
                .verifyComplete();
    }


    /**
     * Zip is a special kind of merging methodology
     * flax1[0] + flax2[0]
     * flax1[1] + flax2[1]
     * flax1[2] + flax2[2]
     *
     * Once any flax send complete event then rest of the data in other flax will not be processed.
     */
    @Test
    public void testFlux_Zip(){
        Flux.zip( Flux.fromIterable(techs),Flux.just("A","B","C"), (t1,t2) -> {
            return t1.concat(t2);
        })
                .log()
                .subscribe(System.out::println);
        /*StepVerifier.create(flux.log())
                .expectSubscription()
                .verifyComplete();*/
    }

    @Test
    public void testFlux_Zip_With_Delay(){
        Flux.zip( Flux.fromIterable(techs).delayElements(Duration.ofSeconds(1)),
                Flux.just("A","B","C"),
                (t1,t2) -> {
                      return t1.concat(t2);
                })
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void testMono_Zip(){
        Mono<String> mono1 = Mono.just("Reactive Streams");
        Mono<String> mono2 = Mono.just("Spring Boot : Web Flux");

        Mono.zip(mono1,mono2)
        .log()
        .subscribe(System.out::println);
    }
}
