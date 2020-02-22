package com.learn.reactive.config;

import com.learn.reactive.handler.SampleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Autowired
    SampleHandler sampleHandler;

    @Bean
    public RouterFunction<ServerResponse> sampleHandlerRouter(){

        RouterFunction<ServerResponse> route = RouterFunctions
                .route(RequestPredicates.GET("/flux/get").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        sampleHandler::flux)
                .andRoute(RequestPredicates.GET("/hello/handler"),sampleHandler::helloHandler);
        return route;
    }
}
