package com.learn.reactive.service;

import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;

    public Flux<AuthorEO> getAllAuthors() {

        return authorRepo.findAll()
                .delayElements(Duration.ofSeconds(1));
    }
}
