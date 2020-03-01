package com.learn.reactive.request;

import com.learn.reactive.constants.enums.BookLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequest {

    private String createdBy;
    private String updatedBy;
    private String name;
    private String author;
    private String publication;
    private int numberOfPages;
    private String publicationDate;
    private BookLanguage language;
    private String title;
}