package com.learn.reactive.utils;

import com.learn.reactive.constants.ResponseCode;
import com.learn.reactive.constants.ResponseMsg;
import com.learn.reactive.entity.AuthorEO;
import com.learn.reactive.model.Author;
import com.learn.reactive.response.AuthorResponse;

import java.util.Objects;

public class AuthorUtils {

    public static AuthorResponse success(String responseMsg, String responseCode,AuthorEO authorEO){
        return new AuthorResponse(true, responseMsg, responseCode, authorEO != null ? new OrikaMapper<>(authorEO, Author.class).map() : null);
    }

    public static AuthorResponse fail(String responseMsg, String responseCode) {

        return new AuthorResponse(false, responseMsg, responseCode, null);
    }
}
