package com.learn.reactive.controller.api.v1;

import com.learn.reactive.constants.ApiEndPoints;
import com.learn.reactive.request.AuthorRequest;
import com.learn.reactive.response.AuthorResponse;
import com.learn.reactive.response.CounterResponse;
import com.learn.reactive.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiEndPoints.AUTHOR + ApiEndPoints.V1)
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping(value = ApiEndPoints.GET_API + ApiEndPoints.ALL,produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<AuthorResponse> getAll() {

        return authorService.getAll();
    }

    @GetMapping(ApiEndPoints.ID_PARAM + ApiEndPoints.GET_API)
    public Mono<AuthorResponse> getById(@PathVariable (name = ApiEndPoints.ID, required = true) String id) {

        return authorService.getById(id);
    }

    @GetMapping(ApiEndPoints.COUNT)
    public Mono<CounterResponse> count() {
        return authorService.count();
    }

    @PostMapping(ApiEndPoints.POST_API)
    public Mono<AuthorResponse> create(@RequestBody AuthorRequest request) {

        return authorService.create(request);
    }

    @DeleteMapping(ApiEndPoints.ID_PARAM + ApiEndPoints.DELETE_API)
    public Mono<AuthorResponse> deleteById(@PathVariable (name = ApiEndPoints.ID) String id,
                                           @RequestParam (name = "deleteBooks", required = false) boolean deleteBooks) {

        return authorService.deleteById(id, Mono.just(deleteBooks));
    }

    @DeleteMapping(ApiEndPoints.DELETE_API + ApiEndPoints.ALL)
    public Mono<AuthorResponse> deleteAll() {

        return authorService.deleteAll();
    }

    @PutMapping(ApiEndPoints.ID_PARAM + ApiEndPoints.PUT_API)
    public Mono<AuthorResponse> update(@RequestBody AuthorRequest request,
                                       @PathVariable (name = ApiEndPoints.ID, required = true) String id) {

        return authorService.update(id, request);
    }
}
