package com.learn.reactive.handler;

import com.learn.reactive.response.BookResponse;
import com.learn.reactive.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BooksHandler {

    @Autowired
    BookService bookService;

    public Mono<ServerResponse> getAllBooks(ServerRequest request) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(bookService.findAll(), BookResponse.class);
    }

}
