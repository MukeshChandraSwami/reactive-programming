package com.learn.reactive.repository;

import com.learn.reactive.entity.BookEO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends ReactiveMongoRepository<BookEO,String> {

}
