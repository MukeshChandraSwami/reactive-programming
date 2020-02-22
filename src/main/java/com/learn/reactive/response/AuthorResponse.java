package com.learn.reactive.response;

import com.learn.reactive.model.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {

    private boolean success;
    private String responseMassage;
    private String responseCode;

    private Author author;
}
