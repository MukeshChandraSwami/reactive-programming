package com.learn.reactive.service;

import com.learn.reactive.constants.ResponseCode;
import com.learn.reactive.constants.ResponseMsg;
import com.learn.reactive.entity.BookEO;
import com.learn.reactive.repository.BookRepo;
import com.learn.reactive.request.BookRequest;
import com.learn.reactive.response.BookResponse;
import com.learn.reactive.response.CounterResponse;
import com.learn.reactive.utils.BookUtils;
import com.learn.reactive.utils.OrikaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorService authorService;

    public Flux<BookResponse> findAll() {
        return bookRepo.findAll()
                .map(bookEO -> {
                    return BookUtils.success(ResponseMsg.FOUND, ResponseCode.FOUND, bookEO);
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.BOOKS_NOT_FOUND, ResponseCode.NOT_FOUND));
    }

    public Mono<BookResponse> findById(String id) {
        return bookRepo.findById(id)
                .map(bookEO -> {
                    return BookUtils.success(ResponseMsg.FOUND, ResponseCode.FOUND,bookEO);
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
    }

    public Flux<BookResponse> findByAuthorId(String authorId) {
        return bookRepo.findByAuthor(authorId)
                .map(bookEO -> {
                    return BookUtils.success(ResponseMsg.FOUND, ResponseCode.FOUND,bookEO);
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
    }

    public Mono<BookResponse> create(BookRequest request) {

       return authorService.getById(request.getAuthor())
                .flatMap(authorResponse -> {
                    if(authorResponse.isSuccess() && authorResponse.getResponseCode().equalsIgnoreCase(ResponseCode.FOUND)) {
                        return bookRepo.save(new OrikaMapper<>(request,BookEO.class).map())
                                .map(bookEO -> {
                                    return BookUtils.success(ResponseMsg.CREATED, ResponseMsg.CREATED,bookEO);
                                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_CREATED,ResponseMsg.NOT_CREATED));
                    } else {
                        return Mono.just(BookUtils.fail(ResponseMsg.AuthorResponseMsg.INVALID_AUTHOR, ResponseCode.AuthorResponseCode.INVALID_AUTHOR));
                    }
                })
                .defaultIfEmpty(BookUtils.fail(ResponseMsg.FAILED, ResponseCode.FAILED));
    }

    public Mono<BookResponse> deleteById(String id) {
        return bookRepo.findById(id)
                .flatMap(bookEO -> {
                    return bookRepo.deleteById(id)
                            .then(findById(id))
                            .map(bookResponse -> {
                                if(bookResponse.isSuccess()) {
                                    return BookUtils.fail(ResponseMsg.NOT_DELETED, ResponseCode.NOT_DELETED);
                                } else {
                                    return BookUtils.success(ResponseMsg.DELETED, ResponseCode.DELETED, null);
                                }
                            });
                })
                .defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
    }

    public Mono<BookResponse> deleteAll() {

        return bookRepo.count()
                .flatMap(bookCounter -> {
                    if(bookCounter > 0) {
                        return bookRepo.deleteAll()
                                .then(bookRepo.count())
                                .map(bookCounterAfterDeletion -> {
                                    if(bookCounterAfterDeletion > 0) {
                                        return BookUtils.fail(ResponseMsg.NOT_DELETED, ResponseCode.NOT_DELETED);
                                    } else {
                                        return BookUtils.success(ResponseMsg.DELETED, ResponseCode.DELETED, null);
                                    }
                                });
                    } else {
                        return Mono.just(BookUtils.success(ResponseMsg.NO_DATA_FOUND, ResponseCode.NO_DATA_FOUND, null));
                    }
                })
                .defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
    }

    public Mono<BookResponse> deleteByAuthor(String authorId) {

        // TO-DO : This is not working correctly. Update it.
        return authorService.getById(authorId)
                .flatMap(authorResponse -> {
                    if(authorResponse.isSuccess() && authorResponse.getResponseCode().equalsIgnoreCase(ResponseCode.FOUND)) {
                        return bookRepo.deleteByAuthor(authorId)
                                .thenMany(findByAuthorId(authorId))
                                .next()
                                .map(bookResponse -> {
                                    System.out.println("Book Response :- " + bookResponse);
                                    if(bookResponse.isSuccess() && bookResponse.getResponseCode().equalsIgnoreCase(ResponseCode.FOUND)) {
                                        return BookUtils.fail(ResponseMsg.NOT_DELETED, ResponseCode.NOT_DELETED);
                                    } else if(bookResponse.getResponseCode().equalsIgnoreCase(ResponseCode.NOT_FOUND)) {
                                        return BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND);
                                    } else {
                                        return BookUtils.success(ResponseMsg.DELETED, ResponseCode.DELETED, null);
                                    }
                                });
                    } else {
                        return Mono.just(BookUtils.fail(ResponseMsg.AUTHORS_NOT_FOUD,ResponseCode.NOT_FOUND));
                    }
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.FAILED,ResponseCode.FAILED));
    }

    public Mono<BookResponse> update(String id, BookRequest request) {

        return authorService.getById(request.getAuthor())
                .flatMap(authorResponse -> {
                    if(authorResponse.isSuccess() && authorResponse.getResponseCode().equalsIgnoreCase(ResponseCode.FOUND)) {
                        return bookRepo.findById(id)
                                .flatMap(bookEO -> {
                                    BookEO updatedBook = new OrikaMapper<>(request,BookEO.class).map();
                                    updatedBook.setId(bookEO.getId());
                                    updatedBook.setCreatedBy(bookEO.getCreatedBy());
                                    updatedBook.setCreatedAt(bookEO.getCreatedAt());

                                    return bookRepo.save(updatedBook)
                                            .map(updatedBookEO -> {
                                                return BookUtils.success(ResponseMsg.UPDATED, ResponseCode.UPDATED, updatedBookEO);
                                            })
                                            .defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_UPDATED,ResponseCode.NOT_UPDATED));
                                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
                    } else {
                        return Mono.just(BookUtils.fail(ResponseMsg.AuthorResponseMsg.INVALID_AUTHOR, ResponseCode.AuthorResponseCode.INVALID_AUTHOR));
                    }
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.FAILED,ResponseCode.FAILED));
    }

    public Mono<CounterResponse> count() {
        return bookRepo.count()
                .map(counter -> {
                    return new CounterResponse(true, ResponseMsg.SUCCESS, ResponseCode.SUCCESS, counter);
                }).defaultIfEmpty(new CounterResponse(false,ResponseMsg.FAILED,ResponseCode.FAILED,0L));
    }

}
