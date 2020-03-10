package com.learn.reactive.service;

import com.learn.reactive.entity.NotificationEO;
import com.learn.reactive.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;

    public Flux<NotificationEO> stream() {
        return notificationRepo.findNotificationBy();
    }
}
