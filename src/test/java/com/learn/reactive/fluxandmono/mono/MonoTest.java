package com.learn.reactive.fluxandmono.mono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void monoTest(){

        Mono<String> mono = Mono.just("Reactive Programming")
                .log();

        mono.subscribe(System.out::println);
    }

    @Test
    public void monoTest_No_Error(){
        Mono<String> mono = Mono.just("Java with reactive programming")
                .log();

        StepVerifier.create(mono)
               // .expectNextCount(1)
                .expectNext("Java with reactive programming")
                .verifyComplete();
    }

    @Test
    public void monoTest_With_Error_Without_Step_Verifier(){
        Mono<String> mono = Mono.just("Java with reactive programming")
                .log();

        mono.mergeWith(Mono.just("Another Mono"));
        mono.concatWith(Mono.error(new Exception("Exception Occurred")));

        mono.subscribe(System.out::println);
    }

    @Test
    public void monoTest_With_Error(){

        Mono.just(new Exception("Runtime Exception"))
                .log()
                .subscribe(objectMono -> System.out.println(objectMono));
    }

    @Test
    public void testMono_hasElement() {

        Mono<Boolean> booleanMono = Mono.empty()
                .hasElement();
        booleanMono.subscribe(System.out::println);
    }
}
