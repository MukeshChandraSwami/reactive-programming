package com.learn.reactive.config;

import com.learn.reactive.handler.SampleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

//@Configuration
public class RouterConfiguration {

    /*@Bean
    public RouterFunction<ServerResponse> sampleHandlerRouter(SampleHandler sampleHandler){

        return RouterFunctions
                .route(request -> {},sampleHandler::flux)
    }*/
}
