package com.learn.reactive.config;

import com.learn.reactive.handler.BooksHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BooksRouter {

    @Bean
    public RouterFunction<ServerResponse> bookRouter(BooksHandler handler) {
        return null;
    }
}
