package com.learn.reactive.repository;

import com.learn.reactive.entity.BookEO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepo extends ReactiveMongoRepository<BookEO,String> {

    Flux<BookEO> findByAuthor(String author);

    Mono<Void> deleteByAuthor(String author);
}
