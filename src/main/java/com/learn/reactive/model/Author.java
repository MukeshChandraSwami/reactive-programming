package com.learn.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private String id;
    private String name;
    private String contactNum;
    private List<String> books;
}
