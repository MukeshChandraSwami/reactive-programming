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

    public Flux<BookResponse> findAll() {
        return bookRepo.findAll()
                .map(bookEO -> {
                    return BookUtils.success(ResponseMsg.FOUND, ResponseCode.FOUND, bookEO);
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.BOOKS_NOT_FOUND, ResponseCode.NOT_FOUND));
    }

    public Mono<BookResponse> getById(String id) {
        return bookRepo.findById(id)
                .map(bookEO -> {
                    return BookUtils.success(ResponseMsg.FOUND, ResponseCode.FOUND,bookEO);
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
    }

    public Mono<BookResponse> create(BookRequest request) {
        return bookRepo.save(new OrikaMapper<BookRequest, BookEO>(request,BookEO.class).map())
                .map(bookEO -> {
                    return BookUtils.success(ResponseMsg.CREATED, ResponseMsg.CREATED,bookEO);
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_CREATED,ResponseMsg.NOT_CREATED));
    }

    public Mono<BookResponse> deleteById(String id) {
        return bookRepo.findById(id)
                .flatMap(bookEO -> {
                    return bookRepo.deleteById(id)
                            .then(getById(id))
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

    public Mono<BookResponse> update(String id, BookRequest request) {

        return bookRepo.findById(id)
                .flatMap(bookEO -> {
                    BookEO updatedBook = new OrikaMapper<BookRequest,BookEO>(request,BookEO.class).map();
                    updatedBook.setId(bookEO.getId());
                    updatedBook.setCreatedBy(bookEO.getCreatedBy());
                    updatedBook.setCreatedAt(bookEO.getCreatedAt());

                    return bookRepo.save(updatedBook)
                            .map(updatedBookEO -> {
                                return BookUtils.success(ResponseMsg.UPDATED, ResponseCode.UPDATED, updatedBookEO);
                            })
                            .defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_UPDATED,ResponseCode.NOT_UPDATED));
                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.NOT_FOUND,ResponseCode.NOT_FOUND));
    }

    public Mono<CounterResponse> count() {
        return bookRepo.count()
                .map(counter -> {
                    return new CounterResponse(true, ResponseMsg.SUCCESS, ResponseCode.SUCCESS, counter);
                }).defaultIfEmpty(new CounterResponse(false,ResponseMsg.FAILED,ResponseCode.FAILED,0L));
    }

}
