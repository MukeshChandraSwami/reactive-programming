package com.learn.reactive.controller.api.v2;

import com.learn.reactive.constants.ApiEndPoints;
import com.learn.reactive.entity.NotificationEO;
import com.learn.reactive.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = ApiEndPoints.NOTIFICATION + ApiEndPoints.V2 + ApiEndPoints.STREAM, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<NotificationEO> streamNotifications() {

        return notificationService.stream();
    }
}
