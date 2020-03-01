package com.learn.reactive.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

    private String createdBy;
    private String updatedBy;
    private String name;
    private String contactNum;
}
