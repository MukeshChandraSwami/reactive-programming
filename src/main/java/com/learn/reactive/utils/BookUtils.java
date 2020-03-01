package com.learn.reactive.utils;

import com.learn.reactive.entity.BookEO;
import com.learn.reactive.model.Book;
import com.learn.reactive.response.BookResponse;

public class BookUtils {


    public static BookResponse fail(String responseMsg, String responseCode) {
        BookResponse response = new BookResponse();
        response.setResponseMassage(responseMsg);
        response.setResponseCode(responseCode);
        return response;
    }

    public static BookResponse success(String responseMsg, String responseCode, BookEO bookEO) {
        return new BookResponse(true, responseMsg, responseCode,
                bookEO != null ? bookEO.getCreatedAt() : null,
                bookEO != null ? bookEO.getUpdatedAt() : null,
                bookEO != null ? bookEO.getCreatedBy() : null,
                bookEO != null ? bookEO.getUpdatedBy() : null,
                new OrikaMapper<BookEO, Book>(bookEO,Book.class).map());
    }
}
