package com.learn.reactive.fluxandmono.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FluxFactoryMethods {

    private static final List<String> techs = Arrays.asList("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor");
    private static final String arr[] = new String[] {"Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor"};
    /*
    Flux can be created in multiple ways. We will go through most of the factory methods of Flux.
    1. just()
    2. fromIterable() : Creating Flux from Collections that implements Iterable<> interface
    3. fromArray() : Creating Flux form array
    4. fromStream() : Creating Flux form any Stream
     */

    /**
     *  Creating flux with just()
     */
    @Test
    public void testFlux_With_just(){
        Flux<String> flux = Flux.just("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor")
                .log();
        flux.subscribe(System.out::println);
    }

    /**
     *  Creating flux with fromIterable()
     */
    @Test
    public void testFlux_With_Collection_List_With_fromIterable(){

        Flux<String> techFlux = Flux.fromIterable(techs)
                .log();

        techFlux.subscribe(System.out::println);
    }

    @Test
    public void testFlux_Map_With_fromIterable(){

        Map<String, String> map = new HashMap<>();

        map.put("Programming","Java");
        map.put("DevOps", "Docker");
        Flux.fromIterable(map.entrySet())
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void testFlux_With_fromArray(){

        Flux.fromArray(arr)
                .log()
                .subscribe(System.out::println);

    }

    @Test
    public void testFlux_With_Stream(){

        Stream<String> strm = Stream.of("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor");
        Flux.fromStream(strm)
                .log()
                .subscribe(ob -> System.out.println(ob));
    }

    @Test
    public void testFlux_With_Supplier(){

        Supplier<Stream<String>> supplier = () -> Stream.of("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor");
        Flux.fromStream(supplier.get());
    }

    @Test
    public void testFlux_With_rang(){

        Flux.range(1,10)
                .log()
                .subscribe(System.out::println);
    }
}
