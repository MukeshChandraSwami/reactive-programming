package com.learn.reactive.fluxandmono.flux;

import com.learn.reactive.model.Author;
import lombok.SneakyThrows;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static reactor.core.scheduler.Schedulers.parallel;

/**
 *  Adding filters over Flux
 *
 *  Flux class contains method filter()
 *  This method is responsible for filtering the content of Flux.
 *  This method takes an argument of Predicate
 */
public class FluxFilterTest {

    private static final List<String> techs = Arrays.asList("Reactive", "Streams", "Lambda", "Publisher", "Subscriber", "Subscription", "Processor");

    /**
     * Filter logic can be anything
     * We are filtering here via startsWith() function.
     */
    @Test
    public void testFlux_Filter(){

       Flux<String> flux = Flux.fromIterable(techs)
                .filter(str -> str.startsWith("S") || str.startsWith("s"));


        StepVerifier.create(flux.log())
                .expectNext("Streams")
                .expectNext("Subscriber","Subscription")
                .verifyComplete();
    }

    @Test
    public void testFlux_Filter_Mapping(){

        Flux.fromIterable(techs)
                .filter(str -> str.length() > 6)
                .map(str -> str.substring(0,6))
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void testFlux_Transform_Using_Map(){

        Flux<Integer> flux = Flux.fromIterable(techs)
                .map(str -> str + " -> " + str.length())
                .map(str -> str.length());

        StepVerifier.create(flux.log())
                .expectNextMatches(i -> i > 11)
                .verifyComplete();
    }

    /*
    * Flat map is basically FLux of Flux OR Stream of Stream
    * If we want to perform some operation with external resource like DB then we use this.
    * */

    @Test
    public void testFlux_FlatMap(){

        Flux<String> flux = Flux.fromIterable(techs)
                .flatMap(str -> {
                    return Flux.fromIterable(getList(str));
                });

        StepVerifier.create(flux.log())
                .expectNextCount(techs.size() * 2)
                .verifyComplete();

    }

    @Test
    public void testFlux_MultiThreading_FlatMap_Without_Sequence(){

        Flux<String> stringFlux = Flux.fromIterable(techs)
                .window(2)      // It will return Flux<Flux<String>> means Flux of Flux
                .flatMap(flux -> {
                    return flux.map(this::getList)      // Flux<List<String>>
                            .subscribeOn(parallel())    // Moved on multiple threads
                            .flatMap(s -> Flux.fromIterable(s)); // Flux<String>
                })
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(techs.size() * 2)
                .verifyComplete();
    }

    @Test
    public void testFlux_MultiThreading_FlatMap_Sequence(){

        Flux<String> stringFlux = Flux.fromIterable(techs)
                .window(2)
                .flatMapSequential(flux ->
                        flux.map(this::getList)
                               .subscribeOn(parallel())
                               .flatMap(s -> Flux.fromIterable(s))
                ).log();

        StepVerifier.create(stringFlux)
                .expectNextCount(techs.size() * 2)
                .verifyComplete();
    }

    /*
    * Practice over flatMap and its types
    * */
    @Test
    public void testFlatMap(){

        Flux<Author> flux = Flux.just("1","2","3","4","5")
                .flatMap(id -> this.getEmploye(id));

        flux.subscribe(val -> System.out.println(val),
                e -> System.out.println(e));
    }

    @Test
    public void testFlatMapWithWindow(){

        Flux<Author> flux = Flux.just("1","2","3","4","5")
                .window(2)
                .flatMap(stringFlux -> stringFlux
                        .subscribeOn(parallel())
                        .flatMap(this::getEmploye)
                );

        flux.log()
                .subscribe(/*val -> System.out.println(val),
                e -> System.out.println(e)*/);
    }

    @SneakyThrows
    private Flux<Author> getEmploye(String empId){

        Map<String, Author> authors = new HashMap<>();
        authors.put("1", new Author("Sonam","1","book1"));
        authors.put("2", new Author("Nitesh","2","book2"));
        authors.put("3", new Author("Rakesh","3","book3"));
        authors.put("4", new Author("Lizel","4","book4"));
        authors.put("5", new Author("Ganesha","5","book5"));

        Thread.sleep(1000);
        return Flux.just(authors.get(empId));
    }



    private List<String> getList(String str){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return Arrays.asList(str , "new");
    }

}
