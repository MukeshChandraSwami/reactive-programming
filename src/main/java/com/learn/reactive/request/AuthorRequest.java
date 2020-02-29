package com.learn.reactive.request;

import com.learn.reactive.request.common.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest extends BaseRequest {

    private String name;
    private String contactNum;
    private List<String> books;
}
