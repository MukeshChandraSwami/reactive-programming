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

        /*
        * 1. Check if author is valid or not
        * 2. Check if books of that author is available to delete or not via counting it.
        * 3. Delete all books of the author.
        * 4. Revalidate second step to ensure that books has been deleted.
        * */

        return authorService.getById(authorId)  // 1
                .flatMap(authorResponse -> {
                    if(authorResponse.isSuccess() && authorResponse.getResponseCode().equalsIgnoreCase(ResponseCode.FOUND)) {
                       return countByAuthor(authorId)   // 2
                                .flatMap(bookCounterResponse -> {
                                    if(bookCounterResponse.isSuccess()
                                            && bookCounterResponse.getResponseCode().equalsIgnoreCase(ResponseCode.SUCCESS)) {
                                        if(bookCounterResponse.getCount() > 0) {
                                            return bookRepo.deleteByAuthor(authorId)    // 3
                                                    .then(countByAuthor(authorId))  // 4
                                                    .map(bookCounterResponseAfterDeletion -> {
                                                        if(bookCounterResponseAfterDeletion.isSuccess()
                                                                && bookCounterResponseAfterDeletion.getResponseCode().equalsIgnoreCase(ResponseCode.SUCCESS)) {
                                                            return BookUtils.success(ResponseMsg.DELETED, ResponseCode.DELETED, null);
                                                        } else {
                                                            return BookUtils.fail(ResponseMsg.FAILED,ResponseCode.FAILED);
                                                        }
                                                    });
                                        } else {
                                            return Mono.just(BookUtils.success(ResponseMsg.NO_DATA_FOUND, ResponseCode.NO_DATA_FOUND, null));
                                        }
                                    } else {
                                        return Mono.just(BookUtils.fail(ResponseMsg.FAILED, ResponseCode.FAILED));
                                    }
                                }).defaultIfEmpty(BookUtils.fail(ResponseMsg.FAILED,ResponseCode.FAILED));
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

    public Mono<CounterResponse> countByAuthor(String authorId) {

        return authorService.getById(authorId)
                .flatMap(authorResponse -> {
                    if(authorResponse.isSuccess() && authorResponse.getResponseCode().equalsIgnoreCase(ResponseCode.SUCCESS)) {
                        return bookRepo.countByAuthor(authorId)
                                .map(counter -> {
                                    return new CounterResponse(true, ResponseMsg.SUCCESS, ResponseCode.SUCCESS, counter);
                                }).defaultIfEmpty(new CounterResponse(false, ResponseMsg.FAILED, ResponseCode.FAILED,0L));
                    } else {
                        return Mono.just(new CounterResponse(false, ResponseMsg.AUTHORS_NOT_FOUD, ResponseCode.NOT_FOUND, 0L));
                    }
                }).defaultIfEmpty(new CounterResponse(false,ResponseMsg.FAILED,ResponseCode.FAILED,0L));
    }

}
