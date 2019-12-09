package com.learn.reactive.fluxandmono.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class ErrorHandling {

    /**
     *  We can handle error by using onErrorResume()
     *  We can return any other flux or can do any other operation as needed.
     */
    @Test
    public void testFlux_Handle_Error_onErrorResume(){

        Flux<String> flux = Flux.just("A", "B")
                .concatWith(Flux.just("C", "D"))
                .concatWith(Flux.error(new RuntimeException("Exception : Runtime exception is occurred.")))
                .concatWith(Flux.just("After Error"));

        flux.log()
                .onErrorResume(RuntimeException.class,e -> {
                    System.out.println("Exception is :- " + e.getMessage());
                    return Flux.just("Resumed after error.");
                })
                .subscribe(System.out::println);
    }

    @Test
    public void testFlux_Handle_Error_Multiple_onErrorResume(){

        Flux<String> flux = Flux.just("A", "B")
                .concatWith(Flux.just("C", "D"))
                .concatWith(Flux.error(new RuntimeException("Exception : Runtime exception is occurred.")))
                .concatWith(Flux.error(new NullPointerException("Exception : Null pointer exception is occurred.")))
                .concatWith(Flux.just("After Error"));

        flux.log()
                .onErrorResume(NullPointerException.class, e -> {
                    System.out.println("Exception is :- " + e.getMessage());
                    return Flux.just("Resumed after NullPointerException.");
                })
                .onErrorResume(RuntimeException.class, e -> {
                    System.out.println("Exception is :- " + e.getMessage());
                    return Flux.just("Resumed after RuntimeException.");
                })
                .subscribe(System.out::println);
    }

    /**
     * In this situation we can return any fallback value.
     * we can say any default value that we want to send in case of error.
     * Control will be return back to caller as soon as any error is occurred.
     */
    @Test
    public void testFlux_Handle_Error_onErrorReturn(){

        Flux<String> flux = Flux.just("A", "B")
                .concatWith(Flux.just("C", "D"))
                .concatWith(Flux.error(new RuntimeException("Exception : Runtime exception is occurred.")))
                .concatWith(Flux.just("After Error"));

        flux.log()
                .onErrorReturn("Fallback value of Runtime Exception class")
                .subscribe(obj -> {
                    System.out.println(obj);
                });
    }

    /**
     * In case of multiple fallback return only very first fallback will be executed and stopped the execution.
     */
    @Test
    public void testFlux_Handle_Error_Multiple_onErrorReturn(){

        Flux<String> flux = Flux.just("A", "B")
                .concatWith(Flux.just("C", "D"))
                .concatWith(Flux.error(new RuntimeException("Exception : Runtime exception is occurred.")))
                .concatWith(Flux.error(new NullPointerException("Exception : Null pointer exception is occurred.")))
                .concatWith(Flux.just("After Error"));

        flux.log()
                .onErrorReturn(NullPointerException.class, "Fallback value of Null pointer Exception class")
                .onErrorReturn(RuntimeException.class,"Fallback value of Runtime Exception class")
                .subscribe(System.out::println);
    }

    /**
     * We can map one exception to another by using this method.
     * Retry works in case of any error. It try to do operation again as specified number.
     * We can also specify time duration between retries by using retryBackOff(retry,duration)
     */
    @Test
    public void testFlux_Handle_Error_onErrorMap(){

        Flux<String> flux = Flux.just("A", "B")
                .concatWith(Flux.just("C", "D"))
                .concatWith(Flux.error(new RuntimeException("Exception : Runtime exception is occurred.")))
                .concatWith(Flux.just("After Error"))
                .onErrorMap(RuntimeException.class, e -> {
                    return new ClassCastException("Class cast exception");
                })
                .retry(2);

        flux.log()
                .subscribe(obj -> {
                    System.out.println(obj);
                });
    }
}
