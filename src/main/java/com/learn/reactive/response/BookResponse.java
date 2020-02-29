package com.learn.reactive.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.reactive.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class BookResponse {

    private boolean success;
    private String responseMassage;
    private String responseCode;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    private Book book;
}
