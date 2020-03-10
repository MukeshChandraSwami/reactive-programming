package com.learn.reactive.service;

import com.learn.reactive.constants.ResponseCode;
import com.learn.reactive.constants.ResponseMsg;
import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.repository.AuthorRepo;
import com.learn.reactive.request.AuthorRequest;
import com.learn.reactive.response.AuthorResponse;
import com.learn.reactive.response.CounterResponse;
import com.learn.reactive.utils.AuthorUtils;
import com.learn.reactive.utils.OrikaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;

    @Autowired
    BookService bookService;

    public Flux<AuthorResponse> getAll() {

        return authorRepo.findAll()
                .map(authorEO -> {
                    return AuthorUtils.success(ResponseMsg.FOUND,ResponseCode.FOUND, authorEO);
                })
                .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.AUTHORS_NOT_FOUD, ResponseCode.NOT_FOUND));
    }

    public Mono<AuthorResponse> getById(String id) {
        return authorRepo.findById(id)
                .map(authorEO -> {
                    System.out.println("Author :- " + authorEO);
                    return AuthorUtils.success(ResponseMsg.FOUND,ResponseCode.FOUND,authorEO);
                })
                .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.NOT_FOUND, ResponseCode.NOT_FOUND));
    }

    public Mono<AuthorResponse> create(AuthorRequest request) {
        AuthorEO authorEO = new OrikaMapper<>(request, AuthorEO.class).map();
        return authorRepo.save(authorEO)
                .map(a -> {
                    return AuthorUtils.success(ResponseMsg.CREATED,ResponseCode.CREATED,a);
                })
                .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.NOT_CREATED, ResponseCode.NOT_CREATED));
    }

    public Mono<AuthorResponse> deleteById(String id, Mono<Boolean> deleteBooks) {

        // TODO : Implement deleteBooks functionality.
        return authorRepo.findById(id)
                .flatMap(authorEO -> {
                    return authorRepo.deleteById(id)
                            .then(getById(id))
                            .map(authorResponse -> {
                                   if(authorResponse.isSuccess() && authorResponse.getResponseCode().equalsIgnoreCase(ResponseCode.FOUND)) {
                                        return AuthorUtils.fail(ResponseMsg.NOT_DELETED, ResponseCode.NOT_DELETED);
                                    } else {
                                        return AuthorUtils.success(ResponseMsg.DELETED, ResponseCode.DELETED, null);
                                    }
                            });
                })
                .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.NOT_FOUND, ResponseCode.NOT_FOUND));
    }

    public Mono<AuthorResponse> deleteAll() {
        return authorRepo.count()
                .flatMap(counter -> {
                    if(counter > 0) {
                       return authorRepo.deleteAll()
                                .then(authorRepo.count())
                                .map(countAfterDeletion -> {
                                    if(countAfterDeletion > 0) {
                                        return AuthorUtils.fail(ResponseMsg.NOT_DELETED, ResponseCode.NOT_DELETED);
                                    } else {
                                        return AuthorUtils.success(ResponseMsg.DELETED, ResponseCode.DELETED,null);
                                    }
                                });
                    } else {
                        return Mono.just(AuthorUtils.success(ResponseMsg.NO_DATA_FOUND, ResponseCode.NO_DATA_FOUND, null));
                    }
                })
                .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.NOT_FOUND, ResponseCode.NOT_FOUND));
    }

    public Mono<AuthorResponse> update(String id, AuthorRequest request) {
        return authorRepo.findById(id)
                .flatMap(authorEO -> {
                    AuthorEO updatedAuthor =  new OrikaMapper<>(request, AuthorEO.class).map();
                    updatedAuthor.setId(authorEO.getId());
                    updatedAuthor.setCreatedAt(authorEO.getCreatedAt());
                    updatedAuthor.setCreatedBy(authorEO.getCreatedBy());
                    return authorRepo.save(updatedAuthor)
                            .map(a -> {
                                return AuthorUtils.success(ResponseMsg.UPDATED,ResponseCode.UPDATED,a);
                            })
                            .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.NOT_UPDATED, ResponseCode.NOT_UPDATED));
                })
                .defaultIfEmpty(AuthorUtils.fail(ResponseMsg.NOT_FOUND, ResponseCode.NOT_FOUND));
    }

    public Mono<CounterResponse> count() {
        return authorRepo.count()
                .map(authorCounter -> {
                    return new CounterResponse(true,ResponseMsg.SUCCESS,ResponseCode.SUCCESS,authorCounter);
                }).defaultIfEmpty(new CounterResponse(false,ResponseMsg.FAILED, ResponseCode.FAILED,null));
    }
}
