package com.learn.reactive.service;

import com.learn.reactive.constants.ResponseCode;
import com.learn.reactive.constants.ResponseMsg;
import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.repository.AuthorRepo;
import com.learn.reactive.request.AuthorRequest;
import com.learn.reactive.response.AuthorResponse;
import com.learn.reactive.utils.AuthorUtils;
import com.learn.reactive.utils.OrikaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;

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

    public Mono<AuthorResponse> deleteById(String id) {

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

    public Mono<AuthorResponse> update(AuthorRequest request) {
        return null;
    }
}
