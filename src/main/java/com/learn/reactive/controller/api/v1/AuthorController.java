package com.learn.reactive.controller.api.v1;

import com.learn.reactive.constants.ApiEndPoints;
import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.request.AuthorRequest;
import com.learn.reactive.response.AuthorResponse;
import com.learn.reactive.response.DeleteResponse;
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
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiEndPoints.AUTHOR + ApiEndPoints.V1)
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping(value = ApiEndPoints.GET_API + ApiEndPoints.ALL,produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<AuthorEO> getAllAuthors() {

        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}"+ApiEndPoints.GET_API)
    public Mono<AuthorResponse> getOtherById(@PathVariable (name = "id", required = true) String id) {

        return null;
    }

    @PostMapping(ApiEndPoints.POST_API)
    public Mono<AuthorResponse> createAuthor(@RequestBody AuthorRequest request) {

        return null;
    }

    @DeleteMapping("/{id}"+ApiEndPoints.DELETE_API)
    public Mono<DeleteResponse> deleteAuthorById(@PathVariable (name = "id", required = true) String id) {

        return null;
    }

    @PutMapping(ApiEndPoints.PUT_API)
    public Mono<AuthorResponse> updateAuthor(@RequestBody AuthorRequest request) {

        return null;
    }
}
