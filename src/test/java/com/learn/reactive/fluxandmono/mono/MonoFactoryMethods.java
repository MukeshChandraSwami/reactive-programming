package com.learn.reactive.fluxandmono.mono;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class MonoFactoryMethods {

    /*
    * We can create Mono in multiple ways :-
    * 1. just() :- We can create mono by using just()
    * 2. fromSupplier() :- We can create Mono by Supplier<>
    * */

    @Test
    public void testMono_With_just(){
        Mono.just("Reactive programming")
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void testMono_With_(){

         Mono.empty();
    }

    @Test
    public void testMono_WIth_fromSupplier(){

        Supplier<Stream<String>> supplier = () -> Stream.of("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor");
        Mono.fromSupplier(supplier)
                .log()
            .subscribe(stream -> {
                stream.map(str -> str.substring(2))
                        .forEach(str -> System.out.println(str));
            });
    }

}
