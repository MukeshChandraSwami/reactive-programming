package com.learn.reactive.utils;

import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.model.Author;
import com.learn.reactive.response.AuthorResponse;

public class AuthorUtils {

    public static AuthorResponse success(String responseMsg, String responseCode,AuthorEO authorEO) {
        return new AuthorResponse(true, responseMsg, responseCode,
                authorEO != null ? authorEO.getCreatedAt() : null,
                authorEO != null ? authorEO.getUpdatedAt() : null,
                authorEO != null ? authorEO.getCreatedBy() : null,
                authorEO != null ? authorEO.getUpdatedBy() : null,
                authorEO != null ? new OrikaMapper<>(authorEO, Author.class).map() : null);
    }

    public static AuthorResponse fail(String responseMsg, String responseCode) {

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setResponseMassage(responseMsg);
        authorResponse.setResponseCode(responseCode);
        return authorResponse;
    }
}
