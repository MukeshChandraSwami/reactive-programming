package com.learn.reactive.repository;

import com.learn.reactive.entity.AuthorEO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AuthorRepo extends ReactiveMongoRepository<AuthorEO,String> {

    Flux<AuthorEO> findByContactNum(String contactNum);
}
