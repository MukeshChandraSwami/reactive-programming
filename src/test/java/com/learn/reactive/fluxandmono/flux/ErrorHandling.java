package com.learn.reactive.fluxandmono.flux;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DirtiesContext
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


    /*
    * Practice over error handling starts from here.
    * */

    @Test
    public void testOnErrorReturn(){

      Flux<Integer> flux =  Flux.just("10","20","30","Ex","40","50")
                .map(this::doSomething)
                .onErrorReturn(e -> e instanceof NumberFormatException,1000);
        flux.subscribe(System.out::println,
                e -> System.out.println("ERROR :- " + e));
    }

    @Test
    public void testOnErrorResumeWithMap(){
        Flux<Integer> flux = Flux.just("10","20","30","Ex","40","50")
                .map(this::doSomething)
                .onErrorResume(e -> {
                    System.out.println("Error :- " + e.getMessage());
                    return Flux.just(1000);
                });

        flux.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void testOnErrorResumeWithFlatMap(){

        Flux<Integer> flux = Flux.just("10","20","30","Ex","40","50")
                .flatMap(str -> parseToInt(str))
                .onErrorResume(e -> e instanceof NullPointerException, e -> {
                    System.out.println("Exception :- " + e.getMessage());
                    return Flux.just(1000);
                });

        flux.subscribe(System.out::println);
    }

    @Test
    public void testOnErrorResume_ReThrowAnotherException(){

        Flux<Integer> flux = Flux.just("10","20","30","Ex","40","50")
                .flatMap(this::parseToInt)
                .onErrorResume(e -> Mono.error(new NullPointerException("User defined exception")));

        flux.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void testOnErrorMap_ReThrowAnotherException(){

        Flux<Integer> flux = Flux.just("10","20","30","Ex","40","50")
                .flatMap(this::parseToInt)
                .onErrorMap(e -> new NullPointerException("User defined exception"));

        flux.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void testDoOnError(){

        Flux<Integer> flux = Flux.just("10","20","30","Ex","40","50")
                .flatMap(this::parseToInt)
                .doOnError(e -> {
                    System.out.println(e);
                });
        flux.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void testDoFinally(){
        Flux<String> flux = Flux.just("10","20","30","Ex","40","50")
                .doFinally(System.out::println)
                .doFinally(signalType -> {
                    System.out.println("2 : " + signalType.name());
                })
                /*.doOnTerminate(() -> {
                    System.out.println("Termination executed");
                })*/
                /*.take(2)*/;

        flux.subscribe(System.out::println);
    }

    private int doSomething(String s) {

        return Integer.parseInt(s);
    }

    private Flux<Integer> parseToInt(String s){

        int i = Integer.parseInt(s);
        return Flux.just(i);
    }
}
