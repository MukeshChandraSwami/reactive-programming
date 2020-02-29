package com.learn.reactive.repository;

import com.learn.reactive.entity.AuthorEO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.stream.Stream;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class AuthorRepoTests {

    Stream<AuthorEO> authors = Arrays.asList(new AuthorEO(null,null,null,null,null, null,"Mukesh","9530105788",null),
            new AuthorEO(null,null,null,null,null, null,"Sonam","8387022222",null),
            new AuthorEO("l1",null,null,null,null, null,"Lucky","8766602057",null)).stream();

    @Autowired
    AuthorRepo authorRepo;

    @Before
    public void setUp() {
        authorRepo.deleteAll()
                .thenMany(Flux.fromStream(authors))
                .flatMap(authorRepo::save)
                .doOnNext(authorEO -> {
                    System.out.println("Author saved :- " + authorEO);
                })
                .blockLast();
    }

    @Test
    public void testFindAll() {

        StepVerifier.create(authorRepo.findAll().log())
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    public void testFindById() {

        StepVerifier.create(authorRepo.findById("l1"))
                .expectSubscription()
                .expectNextMatches(author -> author.getId().equalsIgnoreCase("l1"))
                .verifyComplete();
    }

    @Test
    public void testFindByContactNum() {

        StepVerifier.create(authorRepo.findByContactNum("8387022222"))
                .expectSubscription()
                .expectNextMatches(authr -> authr.getContactNum().equalsIgnoreCase("8387022222"))
                .verifyComplete();
    }
}
