package com.learn.reactive.model;

import com.learn.reactive.constants.enums.BookLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String id;
    private String name;
    private String author;
    private String publication;
    private int numberOfPages;
    private String publicationDate;
    private BookLanguage language;
    private String title;
}
