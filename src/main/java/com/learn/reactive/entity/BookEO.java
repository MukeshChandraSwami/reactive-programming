package com.learn.reactive.entity;

import com.learn.reactive.constants.enums.BookLanguage;
import com.learn.reactive.entity.commons.BaseEO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "book")
@AllArgsConstructor
@NoArgsConstructor
public class BookEO extends BaseEO {

    private String name;
    private String author;
    private String publication;
    private int numberOfPages;
    private String publicationDate;
    private BookLanguage language;
    private String title;
}
