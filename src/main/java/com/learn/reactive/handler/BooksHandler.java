package com.learn.reactive.handler;

import com.learn.reactive.request.BookRequest;
import com.learn.reactive.response.BookResponse;
import com.learn.reactive.response.CounterResponse;
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

    public Mono<ServerResponse> getById(ServerRequest request) {

        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.findById(id), BookResponse.class);
    }

    public Mono<ServerResponse> getByAuthorId(ServerRequest request) {

        String authorId = request.pathVariable("authorId");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(bookService.findByAuthorId(authorId),BookResponse.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<BookRequest> bookRequestMono = request.bodyToMono(BookRequest.class);
        return bookRequestMono.flatMap(bookRequest -> {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookService.create(bookRequest),BookResponse.class);
        });
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String id =  request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.deleteById(id),BookResponse.class);
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.deleteAll(),BookResponse.class);
    }

    public Mono<ServerResponse> deleteByAuthor(ServerRequest request) {

        String authorId =  request.pathVariable("authorId");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.deleteByAuthor(authorId),BookResponse.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        String id = request.pathVariable("id");
        Mono<BookRequest> bookRequestMono = request.bodyToMono(BookRequest.class);
        return bookRequestMono.flatMap(bookRequest -> {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookService.update(id,bookRequest),BookResponse.class);
        });
    }

    public Mono<ServerResponse> count(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.count(), CounterResponse.class);
    }

    public Mono<ServerResponse> countByAuthor(ServerRequest request) {
        String authorId = request.pathVariable("authorId");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.countByAuthor(authorId),CounterResponse.class);
    }

}
