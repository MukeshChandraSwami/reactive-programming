package com.learn.reactive.config;

import com.learn.reactive.constants.ApiEndPoints;
import com.learn.reactive.handler.BooksHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BooksRouter {

    @Autowired
    BooksHandler handler;

    @Bean
    public RouterFunction<ServerResponse> bookRouter() {
        return RouterFunctions
                .route(
                        RequestPredicates.GET(ApiEndPoints.BOOK + ApiEndPoints.V1 + ApiEndPoints.GET_API + ApiEndPoints.ALL)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::getAllBooks
                )
               .andRoute(
                        RequestPredicates.GET(ApiEndPoints.BOOK + ApiEndPoints.V1 + "/{id}" + ApiEndPoints.GET_API)
                       .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                       ,handler::getById
                )
                .andRoute(
                        RequestPredicates.GET(ApiEndPoints.BOOK + ApiEndPoints.V1 + ApiEndPoints.AUTHOR + "/{authorId}" + ApiEndPoints.GET_API)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::getByAuthorId
                )
                .andRoute(
                        RequestPredicates.POST(ApiEndPoints.BOOK + ApiEndPoints.V1 + ApiEndPoints.POST_API)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::create
                )
                .andRoute(
                        RequestPredicates.DELETE(ApiEndPoints.BOOK + ApiEndPoints.V1 + "/{id}" + ApiEndPoints.DELETE_API)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::deleteById
                )
                .andRoute(
                        RequestPredicates.DELETE(ApiEndPoints.BOOK + ApiEndPoints.V1 + ApiEndPoints.DELETE_API + ApiEndPoints.ALL)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::deleteAll
                )
                .andRoute(
                        RequestPredicates.DELETE(ApiEndPoints.BOOK + ApiEndPoints.V1 + ApiEndPoints.AUTHOR + "/{authorId}" + ApiEndPoints.DELETE_API)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::deleteByAuthor
                )
                .andRoute(
                        RequestPredicates.PUT(ApiEndPoints.BOOK + ApiEndPoints.V1 + "/{id}" + ApiEndPoints.PUT_API)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::update
                )
                .andRoute(
                        RequestPredicates.GET(ApiEndPoints.BOOK + ApiEndPoints.V1 + ApiEndPoints.COUNT)
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,handler::count
                );
    }
}
