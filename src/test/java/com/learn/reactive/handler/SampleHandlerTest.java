package com.learn.reactive.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient*/
public class SampleHandlerTest {

    @Autowired
    WebTestClient client;

   // @Test
    public void testHelloHandler() {
        Flux<String> resultFlux = client.get()
                .uri("/hello/handler")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(resultFlux.log())
                .expectSubscription()
                .expectNext("Hello Handler")
                .verifyComplete();
    }

   // @Test
    public void testFluxHandler() {

        Flux<Integer> result = client.get()
                .uri("/flux/get")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectSubscription()
                //.expectNextCount(5)
                .expectNext(1,2,3,4,5)
                .verifyComplete();

    }
}
