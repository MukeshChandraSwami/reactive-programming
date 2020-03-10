package com.learn.reactive.repository;

import com.learn.reactive.entity.NotificationEO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotificationRepo extends ReactiveMongoRepository<NotificationEO, String> {

    @Tailable
    Flux<NotificationEO> findNotificationBy();
}
