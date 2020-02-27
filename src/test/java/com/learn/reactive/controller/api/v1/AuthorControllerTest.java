package com.learn.reactive.controller.api.v1;

import com.learn.reactive.constants.ApiEndPoints;
import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.repository.AuthorRepo;
import com.learn.reactive.response.AuthorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class AuthorControllerTest {

    Stream<AuthorEO> authors = Arrays.asList(new AuthorEO(null,"Mukesh","9530105788"),
            new AuthorEO(null,"Sonam","8387022222"),
            new AuthorEO("l1","Lucky","8766602057")).stream();

    @Autowired
    WebTestClient client;

    @Autowired
    AuthorRepo authorRepo;

    @Before
    public void setUp() {

        authorRepo.deleteAll()
                .thenMany(Flux.fromStream(authors))
                .flatMap(authorRepo::save)
                .doOnNext((a -> {
                    System.out.println(a);
                }))
                .blockLast();
    }

    @Test
    public void testGetAllAuthors() {

        client.get().uri(ApiEndPoints.AUTHOR + ApiEndPoints.V1 + ApiEndPoints.GET_API + ApiEndPoints.ALL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(AuthorResponse.class);
               // .hasSize(3);
    }
}
