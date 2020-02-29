package com.learn.reactive.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

    private String createdBy;
    private String updatedBy;
    private String name;
    private String contactNum;
    private List<String> books;
}
